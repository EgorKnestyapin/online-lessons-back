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
@Schema(name = "CourseNotFoundError", description = "Course not found")
public class CourseNotFoundErrorDto {

    @Schema(description = "Error message", example = "Course with this ID not found")
    private String message;
}
