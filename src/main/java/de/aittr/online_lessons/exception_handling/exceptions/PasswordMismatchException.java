package de.aittr.online_lessons.exception_handling.exceptions;

public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException(String message) {
        super(message);
    }
}
