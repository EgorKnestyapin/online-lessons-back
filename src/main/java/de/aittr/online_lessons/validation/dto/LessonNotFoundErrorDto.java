package de.aittr.online_lessons.validation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "LessonNotFoundError", description = "Lesson not found")
public class LessonNotFoundErrorDto {

    @Schema(description = "Error message", example = "Lesson with this ID not found")
    private String message;
}
