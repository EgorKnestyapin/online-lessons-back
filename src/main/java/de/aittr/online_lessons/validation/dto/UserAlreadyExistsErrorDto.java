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
@Schema(name = "UserAlreadyExistsError", description = "User already exists")
public class UserAlreadyExistsErrorDto {

    @Schema(description = "Error message", example = "User with this email already exists")
    private String message;
}
