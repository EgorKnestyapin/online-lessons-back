package de.aittr.online_lessons.exception_handling.exceptions;

public class CourseDuplicateException extends RuntimeException {
    public CourseDuplicateException(String message) {
        super(message);
    }
}
