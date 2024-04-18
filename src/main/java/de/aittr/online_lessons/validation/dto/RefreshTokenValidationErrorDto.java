package de.aittr.online_lessons.validation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@Schema(name = "RefreshTokenValidationError", description = "Incorrect refresh token")
public class RefreshTokenValidationErrorDto {

    @Schema(description = "Error message", example = "Refresh token is not valid")
    private String message;
}
