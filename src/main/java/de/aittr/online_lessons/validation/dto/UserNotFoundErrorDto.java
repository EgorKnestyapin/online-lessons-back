package de.aittr.online_lessons.validation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@Schema(name = "UserNotFoundError", description = "User is not found")
public class UserNotFoundErrorDto {

    @Schema(description = "Error message", example = "User with this nickname was not found")
    private String message;
}
