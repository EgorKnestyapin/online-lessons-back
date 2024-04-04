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
@Schema(name = "PasswordMismatchError", description = "Password mismatch")
public class PasswordMismatchErrorDto {

    @Schema(description = "Error message", example = "New password and confirm password mismatch")
    private String message;
}
