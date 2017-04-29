package com.uran.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RestResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 2911521644237857778L;
    
    public RestResourceNotFoundException() {
        super();
    }
    
    public RestResourceNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public RestResourceNotFoundException(final String message) {
        super(message);
    }
    
    public RestResourceNotFoundException(final Throwable cause) {
        super(cause);
    }
}
