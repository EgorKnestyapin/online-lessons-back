package de.aittr.online_lessons.exception_handling.exceptions;

public class UserValidationException extends RuntimeException {
    public UserValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
