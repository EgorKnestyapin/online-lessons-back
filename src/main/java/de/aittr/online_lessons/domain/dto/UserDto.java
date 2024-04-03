package de.aittr.online_lessons.domain.dto;

import de.aittr.online_lessons.domain.jpa.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class UserDto {

    private int id;

    @Pattern.List({
            @Pattern(regexp = "[a-zA-Z]{3,10}", message = "Invalid nickname format"),
            @Pattern(regexp = "[a-zA-Z0-9_-]{3,10}", message = "Invalid nickname format")
    })
    private String nickname;

    @Email
    @NotBlank
    private String email;

    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!$#%])[a-zA-Z0-9!$#%]{8,}$",
            message = "Invalid password format"
    )
    private String password;

    private Set<Role> roles = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
