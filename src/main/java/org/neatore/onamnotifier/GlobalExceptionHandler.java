package org.neatore.onamnotifier;

import org.neatore.onamnotifier.exception.AuthException;
import org.neatore.onamnotifier.exception.QueryNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(QueryNotFoundException.class)
    public ResponseEntity<Void> handleNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Void> handleAuthException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Void> handleMissingServletRequestParameterException() {
        return ResponseEntity.badRequest().build();
    }
}
