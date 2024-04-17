package de.aittr.online_lessons.validation.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LessonValidationErrorDtoTest {

    @Test
    void getMessage() {
        LessonValidationErrorDto dto = new LessonValidationErrorDto("Error message123");
        String expected = "Error message123";
        String actual = dto.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    void setMessage() {
        LessonValidationErrorDto dto = new LessonValidationErrorDto("Error message123");
        assertEquals("Error message123", dto.getMessage());
        dto.setMessage("Changed message321");
        assertEquals("Changed message321", dto.getMessage());
    }
}