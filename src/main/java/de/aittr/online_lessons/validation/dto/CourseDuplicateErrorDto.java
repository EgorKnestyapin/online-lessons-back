package de.aittr.online_lessons.validation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@Schema(name = "CourseDuplicateError", description = "Duplicate courses")
public class CourseDuplicateErrorDto {

    @Schema(description = "Error message", example = "Course is already in the cart")
    private String message;
}
