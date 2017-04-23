package com.uran.service.scheduler;

import com.uran.domain.Horse;
import com.uran.domain.Race;
import com.uran.service.RaceService;
import com.uran.service.events.GambleEvent;
import com.uran.service.events.RaceEvent;
import com.uran.service.events.ServiceEvent;
import com.uran.service.processor.RaceProcessor;
import com.uran.service.simulation.RaceSimulationHelper;
import com.uran.util.RandomUtil;
import com.uran.util.horse.HorseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static java.time.LocalDateTime.now;

@Component
//@PropertySource(value= {"classpath:*.properties"})
public final class RaceScheduler implements ApplicationEventPublisherAware {
    private static final Logger LOG = LoggerFactory.getLogger(RaceScheduler.class);
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
   
    private ApplicationEventPublisher stationEventPublisher;
    
    private static final int MIN_BOTS = 30;
    private static final int MAX_BOTS = 50;
    
    private static boolean RACE_IS_RUNNING = false;
    private static boolean USERS_CAN_MAKE_STAKES = false;
    private static Race currentRace = null;
    
    private boolean first = true;
    private LocalDateTime start = null;
    private LocalDateTime finish = null;
    private List<Horse> horsesForRace = null;
    
    private final RaceSimulationHelper helper;
    private final RaceProcessor processor;
    private final RaceService raceService;
    
    public RaceScheduler(RaceSimulationHelper helper, RaceProcessor processor, RaceService raceService) {
        this.helper = helper;
        this.processor = processor;
        this.raceService = raceService;
    }
    
    @Scheduled(cron = "${start.cron.expression}")
    public void startGamble() {
        LOG.info("Race stopped & Gamble Started at {}", now().format(DATE_TIME_FORMATTER));
        
        stationEventPublisher.publishEvent(new GambleEvent(this, "gamble started"));
        
        start = now();

        RACE_IS_RUNNING = false;
        USERS_CAN_MAKE_STAKES = true;
        
        horsesForRace = helper.getHorsesForRace();
        LOG.info("Horses for race {}", horsesForRace);
    
        currentRace =
                raceService.save(
                        new Race(
                                null,
                                start,
                                null,
                                HorseUtil.getSerialized(horsesForRace),
                                "not yet:еще нет"
                        ));
        
        if (first) {
            helper.killBots();
            helper.createBots(MAX_BOTS);
            first = false;
        } else {
            helper.initBots(MIN_BOTS, MAX_BOTS);
        }
        
        helper.fillAccounts();
        helper.startGamble();
    }
    
    @Scheduled(cron = "${race.cron.expression}")
    public void startRace() {
        
        if (start == null) {
            return;
        }
        
        stationEventPublisher.publishEvent(new RaceEvent(this, "race started"));
        
        RACE_IS_RUNNING = true;
        USERS_CAN_MAKE_STAKES = false;
        
        processor.forbidStakeEditing(currentRace.getId());
        
        finish = now(); // stakes to current race are done
        LOG.info("Race started at {}", finish.format(DATE_TIME_FORMATTER));
        
        // TODO race simulation visualisation
    }
    
    @Scheduled(cron = "${service.cron.expression}")
    public void processRaceResult() {
        
        if (finish == null) {
            return;
        }
        
        stationEventPublisher.publishEvent(new ServiceEvent(this, "service started"));
        
        RACE_IS_RUNNING = false;
        USERS_CAN_MAKE_STAKES = false;
        
        LOG.info("Race results processing starts at {}", now().format(DATE_TIME_FORMATTER));
        
        Horse winning = RandomUtil.getRandomHorseFromList(horsesForRace);
        
        currentRace.setFinish(finish);
        currentRace.setWinning(HorseUtil.getSerialized(Collections.singletonList(winning)));
        raceService.update(currentRace);
        
        processor.process(winning.getId(), currentRace.getId());
    }
    
    public static Race getCurrentRace() {
        return currentRace;
    }
    
    public static boolean isRaceIsRunning() {
        return RACE_IS_RUNNING;
    }
    
    public static boolean isUsersCanMakeStakes() {
        return USERS_CAN_MAKE_STAKES;
    }
    
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        stationEventPublisher = applicationEventPublisher;
    }
}