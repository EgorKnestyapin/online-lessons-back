package de.aittr.online_lessons.exception_handling.exceptions;

public class EnrollmentValidationException extends RuntimeException {
    public EnrollmentValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
