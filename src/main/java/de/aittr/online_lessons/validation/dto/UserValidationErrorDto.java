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
@Schema(name = "UserValidationError", description = "Incorrect user fields")
public class UserValidationErrorDto {

    @Schema(description = "Error message", example = "Password must contain at least eight characters")
    private String message;
}
