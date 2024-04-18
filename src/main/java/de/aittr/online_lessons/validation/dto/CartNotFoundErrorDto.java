package de.aittr.online_lessons.validation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@Schema(name = "CartNotFoundError", description = "Cart not found")
public class CartNotFoundErrorDto {

    @Schema(description = "Error message", example = "Cart with this ID not found")
    private String message;
}
