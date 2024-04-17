package de.aittr.online_lessons.domain.dto;

import de.aittr.online_lessons.services.CartService;
import jakarta.validation.constraints.Pattern;
import lombok.*;

/**
 * Class describing the DTO to change password.
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Getter
@Setter
public class ChangePasswordDto {

    /**
     * Previous user password
     */
    private String oldPassword;

    /**
     * New user password
     */
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!$#%])[a-zA-Z0-9!$#%]{8,}$",
            message = "Invalid password format"
    )
    private String newPassword;

    /**
     * Confirm new user password
     */
    private String confirmNewPassword;
}
