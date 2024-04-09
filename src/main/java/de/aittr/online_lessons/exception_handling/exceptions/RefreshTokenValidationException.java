package de.aittr.online_lessons.exception_handling.exceptions;

public class RefreshTokenValidationException extends RuntimeException {
    public RefreshTokenValidationException(String message) {
        super(message);
    }
}
