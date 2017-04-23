package com.uran.web.exception;

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
