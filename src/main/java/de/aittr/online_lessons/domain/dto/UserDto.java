package de.aittr.online_lessons.domain.dto;

import de.aittr.online_lessons.domain.jpa.Role;
import de.aittr.online_lessons.domain.jpa.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Class describing the user DTO {@link User}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class UserDto {

    /**
     * User ID
     */
    private int id;

    /**
     * User nickname
     */
    @Pattern(
            regexp = "^(?![\\d-]+$)(?=(?:[^a-zA-Z]*[a-zA-Z]){3})[a-zA-Z0-9_-]{3,10}$",
            message = "Invalid nickname format"
    )
    private String nickname;

    /**
     * User email
     */
    @Email
    @NotBlank
    private String email;

    /**
     * User password
     */
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!$#%])[a-zA-Z0-9!$#%]{8,}$",
            message = "Invalid password format"
    )
    private String password;

    /**
     * User roles
     */
    private Set<Role> roles = new HashSet<>();

    /**
     * Getter
     *
     * @return User ID
     * @see UserDto#id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter
     *
     * @param id User ID
     * @see UserDto#id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter
     *
     * @return User nickname
     * @see UserDto#nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Setter
     *
     * @param nickname User nickname
     * @see UserDto#nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Getter
     *
     * @return User email
     * @see UserDto#email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter
     *
     * @param email User email
     * @see UserDto#email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter
     *
     * @return User password
     * @see UserDto#password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter
     *
     * @param password User password
     * @see UserDto#password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter
     *
     * @return User roles
     * @see UserDto#roles
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Setter
     *
     * @param roles User roles
     * @see UserDto#roles
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
