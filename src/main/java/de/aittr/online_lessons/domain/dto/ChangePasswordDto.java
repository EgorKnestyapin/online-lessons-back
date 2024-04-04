package de.aittr.online_lessons.domain.dto;

import jakarta.validation.constraints.Pattern;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Getter
@Setter
public class ChangePasswordDto {

    private String oldPassword;

    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!$#%])[a-zA-Z0-9!$#%]{8,}$",
            message = "Invalid password format"
    )
    private String newPassword;

    private String confirmNewPassword;
}
