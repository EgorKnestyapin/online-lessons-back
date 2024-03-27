package de.aittr.online_lessons.exception_handling;

import lombok.Getter;

@Getter
public class ValidationResponse extends Response{
    private String cause;

    public ValidationResponse(String message, String cause) {
        super(message);
        this.cause = cause;
    }
}
