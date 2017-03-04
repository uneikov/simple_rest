package com.uran.service.events;

import org.springframework.context.ApplicationEvent;

abstract class AbstractStationEvent extends ApplicationEvent {
    private String message;
    
    AbstractStationEvent(final Object source, final String message) {
        super(source);
        this.message = message;
    }
    
    String getMessage() {
        return message;
    }
}
