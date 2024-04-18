package de.aittr.online_lessons.exception_handling.exceptions;

public class UserNotAuthenticated extends RuntimeException {
    public UserNotAuthenticated(String message) {
        super(message);
    }
}
