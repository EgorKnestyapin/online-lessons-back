package de.aittr.online_lessons.exception_handling.exceptions;

public class CourseValidationException extends RuntimeException {
    public CourseValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
