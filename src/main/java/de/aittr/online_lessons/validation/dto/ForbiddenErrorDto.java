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
@Schema(name = "ForbiddenError", description = "Forbidden")
public class ForbiddenErrorDto {

    @Schema(description = "Timestamp", example = "2024-04-03T20:36:27.299+00:00")
    private String timestamp;

    @Schema(description = "Status code", example = "403")
    private String status;

    @Schema(description = "Error message", example = "Forbidden")
    private String error;

    @Schema(description = "Path", example = "/api/cart/buy/6")
    private String path;
}
