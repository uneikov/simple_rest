package com.uran.service.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StationEventsListener {
    private static final Logger LOG = LoggerFactory.getLogger(StationEventsListener.class);
    @EventListener({GambleEvent.class})
    public void handleGambleEvents(final GambleEvent gambleEvent) {
        System.out.println("*************************************************************************************");
        LOG.info("*     GambleEventsListener received event: {} at {}", gambleEvent.getMessage(), gambleEvent.getTimestamp());
        System.out.println("*************************************************************************************");
    }
    
    @EventListener({RaceEvent.class})
    public void handleRaceEvents(final RaceEvent raceEvent) {
        System.out.println("*************************************************************************************");
        LOG.info("*     RaceEventsListener received event: {} at {}", raceEvent.getMessage(), raceEvent.getTimestamp());
        System.out.println("*************************************************************************************");
    }
    
    @EventListener({ServiceEvent.class})
    public void handleRaceEvents(final ServiceEvent serviceEvent) {
        System.out.println("*************************************************************************************");
        LOG.info("*     ServiceEventsListener received event: {} at {}", serviceEvent.getMessage(), serviceEvent.getTimestamp());
        System.out.println("*************************************************************************************");
    }
}
