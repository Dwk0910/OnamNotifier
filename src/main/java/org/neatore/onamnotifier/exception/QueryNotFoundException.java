package org.neatore.onamnotifier.exception;

public class QueryNotFoundException extends RuntimeException {
    public QueryNotFoundException(String id) {
        super("Schedule not found with id: " + id);
    }
}
