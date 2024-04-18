package de.aittr.online_lessons.exception_handling.exceptions;

public class LessonDuplicateException extends RuntimeException {
    public LessonDuplicateException(String message) {
        super(message);
    }
}
