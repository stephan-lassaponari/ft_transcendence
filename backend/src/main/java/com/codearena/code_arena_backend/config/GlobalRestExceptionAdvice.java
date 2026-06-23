package com.codearena.code_arena_backend.config;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Global exception handler for all REST controllers.
 *
 * Returns HTTP 200 with a structured body containing the logical status code
 * so the browser's native network-error logger is never triggered.
 * The Angular HTTP interceptor reads the httpStatus field and converts the
 * response back into an HttpErrorResponse for component-level error handlers.
 */
@RestControllerAdvice
public class GlobalRestExceptionAdvice {

    private ResponseEntity<Map<String, Object>> wrap(int status, String message) {
        return ResponseEntity.ok(Map.of("httpStatus", status, "error", message));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> badCredentials(BadCredentialsException ex) {
        return wrap(401, "Invalid username or password");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> badRequest(IllegalArgumentException ex) {
        return wrap(400, ex.getMessage() != null ? ex.getMessage() : "Bad request");
    }

    @ExceptionHandler(io.jsonwebtoken.JwtException.class)
    public ResponseEntity<Map<String, Object>> jwtError(io.jsonwebtoken.JwtException ex) {
        return wrap(400, "Invalid or expired token");
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, Object>> notFound(NoSuchElementException ex) {
        return wrap(404, ex.getMessage() != null ? ex.getMessage() : "Resource not found");
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> conflict(IllegalStateException ex) {
        return wrap(409, ex.getMessage() != null ? ex.getMessage() : "State conflict");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> generic(Exception ex) {
        return wrap(500, "An unexpected error occurred");
    }
}
