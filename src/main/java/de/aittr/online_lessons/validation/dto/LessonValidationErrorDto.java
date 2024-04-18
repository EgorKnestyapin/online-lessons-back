package de.aittr.online_lessons.validation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@Schema(name = "LessonValidationError", description = "Incorrect lesson fields")
public class LessonValidationErrorDto {

    @Schema(description = "Error message", example = "Incorrect values of lesson fields")
    private String message;
}
