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
@Schema(name = "CourseValidationError", description = "Incorrect course fields")
public class CourseValidationErrorDto {

    @Schema(description = "Error message", example = "Incorrect values of course fields")
    private String message;
}
