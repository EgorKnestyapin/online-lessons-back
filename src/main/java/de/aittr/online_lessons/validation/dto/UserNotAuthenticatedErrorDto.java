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
@Schema(name = "UserNotAuthenticated", description = "User is not authenticated")
public class UserNotAuthenticatedErrorDto {

    @Schema(description = "Error message", example = "User is not authenticated")
    private String message;
}
