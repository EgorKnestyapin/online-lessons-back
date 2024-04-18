package de.aittr.online_lessons.security.sec_dto;

import de.aittr.online_lessons.domain.jpa.Role;
import de.aittr.online_lessons.security.sec_service.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * Class describing the authorization information.
 * AuthService is provided for working with authorization information objects {@link AuthService}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
public class AuthInfo implements Authentication {

    /**
     * Information whether the user is authorized
     */
    private boolean authenticated;

    /**
     * User nickname
     */
    private final String username;

    /**
     * User cart ID
     */
    private final int cartId;

    /**
     * User roles
     */
    private final Set<Role> roles;

    /**
     * Constructor for creating user information
     *
     * @param username User nickname
     * @param cartId   User cart ID
     * @param roles    User roles
     */
    public AuthInfo(String username, int cartId, Set<Role> roles) {
        this.username = username;
        this.cartId = cartId;
        this.roles = roles;
    }

    /**
     * Getter
     *
     * @return User roles
     * @see AuthInfo#roles
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    /**
     * Getter
     *
     * @return User nickname
     * @see AuthInfo#username
     */
    @Override
    public Object getPrincipal() {
        return username;
    }

    /**
     * Getter
     *
     * @return Information whether the user is authorized
     * @see AuthInfo#authenticated
     */
    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    /**
     * Setter
     *
     * @param isAuthenticated Information whether the user is authorized
     * @throws IllegalArgumentException Incorrect fields
     * @see AuthInfo#authenticated
     */
    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    /**
     * Getter
     *
     * @return User nickname
     * @see AuthInfo#username
     */
    @Override
    public String getName() {
        return username;
    }

    /**
     * Getter
     *
     * @return User cart ID
     * @see AuthInfo#cartId
     */
    public int getCartId() {
        return cartId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthInfo authInfo = (AuthInfo) o;
        return authenticated == authInfo.authenticated && Objects.equals(username, authInfo.username) && Objects.equals(roles, authInfo.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authenticated, username, roles);
    }

    /**
     * Method to convert a authorization information object to a string representation
     *
     * @return Authorization information object as a string
     */
    @Override
    public String toString() {
        return "AuthInfo{" +
                "authenticated=" + authenticated +
                ", username='" + username + '\'' +
                ", roles=" + roles +
                '}';
    }
}
