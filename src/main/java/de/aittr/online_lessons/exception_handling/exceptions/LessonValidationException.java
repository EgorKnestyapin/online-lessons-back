package de.aittr.online_lessons.exception_handling.exceptions;

public class LessonValidationException extends RuntimeException {
    public LessonValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
