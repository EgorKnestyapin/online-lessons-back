package de.aittr.online_lessons.security.sec_dto;

import de.aittr.online_lessons.security.sec_service.AuthService;
import jakarta.validation.constraints.Email;
import lombok.*;

/**
 * Class describing the token user DTO for login.
 * AuthService is provided for working with token objects {@link AuthService}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class UserLoginDto {

    /**
     * User email
     */
    @Email
    private String email;

    /**
     * User password
     */
    private String password;

    /**
     * Getter
     *
     * @return User email
     * @see UserLoginDto#email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter
     *
     * @param email User email
     * @see UserLoginDto#email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter
     *
     * @return User password
     * @see UserLoginDto#password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter
     *
     * @param password User password
     * @see UserLoginDto#password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
