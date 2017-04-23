package com.uran.service.simulation;


import com.uran.domain.*;
import com.uran.service.AccountService;
import com.uran.service.HorseService;
import com.uran.service.StakeService;
import com.uran.service.UserService;
import com.uran.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.uran.service.scheduler.RaceScheduler.*;

@Component
public class RaceSimulationHelperImpl implements RaceSimulationHelper{
    private static final Logger LOG = LoggerFactory.getLogger(RaceSimulationHelperImpl.class);

    private List<User> bots = new ArrayList<>();
    private List<Horse> selectedHorses = new ArrayList<>();
    private static int botsNumber = 0;
    private static int count = 0;

    private final BotFactoryService botFactoryService;
    private final StakeService stakeService;
    private final UserService userService;
    private final AccountService accountService;
    private final HorseService horseService;

    public RaceSimulationHelperImpl(BotFactoryService botFactoryServiceService,
                                    StakeService stakeService,
                                    UserService userService,
                                    AccountService accountService,
                                    HorseService horseService) {
        this.botFactoryService = botFactoryServiceService;
        this.stakeService = stakeService;
        this.userService = userService;
        this.accountService = accountService;
        this.horseService = horseService;
    }

    @Override
    @Transactional
    public List<Horse> getHorsesForRace(){
        final List<Horse> all = horseService.findAll();
        // set ready to false for all horses
        all.stream().peek(horse -> horse.setReady(false)).forEach(horseService::save);
        // set ready to true for random selected horses and return as List
        selectedHorses =  RandomUtil.getHorsesForRace(all).stream()
                .peek(horse -> horse.setReady(true))
                .collect(Collectors.toList());
        horseService.save(selectedHorses);
        return selectedHorses;
    }

    @Override
    public void createBots(int max){
        botsNumber = max;
        bots = botFactoryService.getBots(botsNumber);
    }

    @Override
    public void initBots(int min, int max){
        botsNumber = ThreadLocalRandom.current().nextInt(min, max);
    }

    @Override
    @Transactional
    public void fillAccounts(){
        bots.forEach(user -> {
            Account account = accountService.get(user.getId());
            account.setBalance(account.getBalance() + 10 + ThreadLocalRandom.current().nextDouble(90.0));
            accountService.update(account);
        });
    }

    @Override
    @Transactional
    public void clearAccounts(){ // no usage ?
        bots.forEach(user -> {
            user.getAccount().setBalance(0.0d);
            accountService.update(user.getAccount());
        });
    }

    @Override
    public void killBots(){
        userService.findAll().stream()
                .filter(user -> user.getName().startsWith("testuser"))
                .forEach(user -> userService.delete(user.getId()));
    }

    @Override
    public void startGamble(){
        final List<Integer> randomTimePoints = RandomUtil.getRandomTimePoints(0, 45, botsNumber);
        LOG.info("Make random time points, race is running? - {}", isRaceIsRunning());
        randomTimePoints.forEach(tick -> {
            while (isUsersCanMakeStakes()){
                if (tick <= LocalDateTime.now().getMinute()){
                    makeStake();
                    break;
                }
            }
        });
        count = 0;
    }
    
    private void makeStake() {
        User botUser = bots.get(count++);
        Double botCash = accountService.get(botUser.getId()).getBalance();
        Double stakeValue = 10 + ThreadLocalRandom.current().nextDouble(90.0);
        stakeValue = stakeValue > botCash ? botCash : stakeValue;
        final Stake stake = stakeService.save(
                new Stake(null,
                        botUser,
                        RandomUtil.getRandomHorseFromList(selectedHorses),
                        getCurrentRace(),
                        stakeValue
                ));
        LOG.info("Bot {} make stake as big as {} at {} minute",
                botUser.getName(), stakeValue, stake.getDateTime().getMinute());
    }
}
