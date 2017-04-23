package com.uran.web.util;

import com.uran.web.exception.RestResourceNotFoundException;

public final class RestPreconditions {
    private RestPreconditions() {
        throw new AssertionError();
    }
   
    /**
     * Check if some value was found, otherwise throw exception.
     *
     * @param expression
     *            has value true if found, otherwise false
     * @throws RestResourceNotFoundException
     *             if expression is false, means value not found.
     */
    public static void checkFound(final boolean expression) {
        if (!expression) {
            throw new RestResourceNotFoundException();
        }
    }
    
    /**
     * Check if some value was found, otherwise throw exception.
     *
     * @param resource
     *            has value true if found, otherwise false
     * @throws RestResourceNotFoundException
     *             if expression is false, means value not found.
     */
    public static <T> T checkFound(final T resource) {
        if (resource == null) {
            throw new RestResourceNotFoundException();
        }
        
        return resource;
    }
    
    /**
     * Check if some value was found, otherwise throw exception.
     *
     * @param resource
     *            has value true if found, otherwise false
     * @param message
     *            has cause of invalidation
     * @throws RestResourceNotFoundException
     *             if expression is false, means value not found.
     */
    public static <T> T checkFound(final T resource, String message) {
        if (resource == null) {
            throw new RestResourceNotFoundException(message);
        }
        
        return resource;
    }
}
