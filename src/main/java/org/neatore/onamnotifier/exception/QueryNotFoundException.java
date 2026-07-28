package org.neatore.onamnotifier.exception;

import java.util.UUID;

public class QueryNotFoundException extends RuntimeException {
    public QueryNotFoundException(UUID id) {
        super("Schedule not found with id: " + id);
    }
}
