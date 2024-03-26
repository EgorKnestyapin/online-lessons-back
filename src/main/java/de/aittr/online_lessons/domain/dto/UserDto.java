package de.aittr.online_lessons.domain.dto;

import de.aittr.online_lessons.domain.jpa.Role;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class UserDto {
    private int id;
    @Setter
    private String nickname;
    @Setter
    private String email;
    @Setter
    private String password;
    private Set<Role> roles = new HashSet<>();
}
