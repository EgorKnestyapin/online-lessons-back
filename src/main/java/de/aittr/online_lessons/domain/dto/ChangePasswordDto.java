package de.aittr.online_lessons.domain.dto;

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
    private String newPassword;
    private String confirmNewPassword;
}
