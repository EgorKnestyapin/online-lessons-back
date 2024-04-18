package de.aittr.online_lessons.validation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@Schema(name = "EnrollmentAlreadyExistsError", description = "Enrollment has already been saved")
public class EnrollmentAlreadyExistsErrorDto {

    @Schema(description = "Error message", example = "Enrollment with that course already exists")
    private String message;
}
