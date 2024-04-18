package de.aittr.online_lessons.validation.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ForbiddenErrorDtoTest {

    @Test
    void getTimestamp() {
        ForbiddenErrorDto dto = new ForbiddenErrorDto("2024-04-15 18:48:31.900084", "403",
                "Forbidden", "/api/lessons/2");
        String expected = "2024-04-15 18:48:31.900084";
        String actual = dto.getTimestamp();

        assertEquals(expected, actual);
    }

    @Test
    void getStatus() {
        ForbiddenErrorDto dto = new ForbiddenErrorDto("2024-04-15 18:48:31.900084", "403",
                "Forbidden", "/api/lessons/2");
        String expected = "403";
        String actual = dto.getStatus();

        assertEquals(expected, actual);
    }

    @Test
    void getError() {
        ForbiddenErrorDto dto = new ForbiddenErrorDto("2024-04-15 18:48:31.900084", "403",
                "Forbidden", "/api/lessons/2");
        String expected = "Forbidden";
        String actual = dto.getError();

        assertEquals(expected, actual);
    }

    @Test
    void getPath() {
        ForbiddenErrorDto dto = new ForbiddenErrorDto("2024-04-15 18:48:31.900084", "403",
                "Forbidden", "/api/lessons/2");
        String expected = "/api/lessons/2";
        String actual = dto.getPath();

        assertEquals(expected, actual);
    }

    @Test
    void setTimestamp() {
        ForbiddenErrorDto dto = new ForbiddenErrorDto("2024-04-15 18:48:31.900084", "403",
                "Forbidden", "/api/lessons/2");
        assertEquals("2024-04-15 18:48:31.900084", dto.getTimestamp());
        dto.setTimestamp("2023-09-17 21:54:25.800053");
        assertEquals("2023-09-17 21:54:25.800053", dto.getTimestamp());
    }

    @Test
    void setStatus() {
        ForbiddenErrorDto dto = new ForbiddenErrorDto("2024-04-15 18:48:31.900084", "403",
                "Forbidden", "/api/lessons/2");
        assertEquals("403", dto.getStatus());
        dto.setStatus("409");
        assertEquals("409", dto.getStatus());
    }

    @Test
    void setError() {
        ForbiddenErrorDto dto = new ForbiddenErrorDto("2024-04-15 18:48:31.900084", "403",
                "Forbidden", "/api/lessons/2");
        assertEquals("Forbidden", dto.getError());
        dto.setError("Conflict");
        assertEquals("Conflict", dto.getError());
    }

    @Test
    void setPath() {
        ForbiddenErrorDto dto = new ForbiddenErrorDto("2024-04-15 18:48:31.900084", "403",
                "Forbidden", "/api/lessons/2");
        assertEquals("/api/lessons/2", dto.getPath());
        dto.setPath("/api/courses/5");
        assertEquals("/api/courses/5", dto.getPath());
    }
}