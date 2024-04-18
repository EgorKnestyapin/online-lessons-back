package de.aittr.online_lessons.domain.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.aittr.online_lessons.services.UserService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class describing the user.
 * UserService is provided for working with user objects {@link UserService}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@Schema(description = "User entity")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "account")
public class User implements UserDetails {

    /**
     * User ID
     */
    @Schema(description = "User unique identifier", example = "11")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * User nickname
     */
    @Schema(description = "Username that use for logging in", example = "Stas9n")
    @NotNull
    @Column(name = "username")
    private String username;

    /**
     * User email
     */
    @Schema(description = "User's email", example = "john@gmail.com")
    @NotNull
    @Column(name = "email")
    private String email;

    /**
     * User password
     */
    @Schema(description = "User's raw password for logging in", example = "Qwerty123$")
    @NotNull
    @Column(name = "password")
    private String password;

    /**
     * User enrollments
     */
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private Set<Enrollment> enrollments = new HashSet<>();

    /**
     * Courses created by the user
     */
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Course> createdCourses = new HashSet<>();

    /**
     * User roles
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(
            name = "account_role",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    /**
     * User-owned cart
     */
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private Cart cart;

    /**
     * User token
     */
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Token token;

    /**
     * Setter
     *
     * @param username User nickname
     * @see User#username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Setter
     *
     * @param email User email
     * @see User#email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Setter
     *
     * @param password User password
     * @see User#password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Setter
     *
     * @param roles User roles
     * @see User#roles
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * Setter
     *
     * @param cart User cart
     * @see User#cart
     */
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    /**
     * Getter
     *
     * @return User email
     * @see User#email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter
     *
     * @return User ID
     * @see User#id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter
     *
     * @param id User ID
     * @see User#id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter
     *
     * @return User enrollments
     * @see User#enrollments
     */
    public Set<Enrollment> getEnrollments() {
        return enrollments;
    }

    /**
     * Setter
     *
     * @param enrollments User enrollments
     * @see User#enrollments
     */
    public void setEnrollments(Set<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    /**
     * Getter
     *
     * @return Courses created by the user
     * @see User#createdCourses
     */
    public Set<Course> getCreatedCourses() {
        return createdCourses;
    }

    /**
     * Setter
     *
     * @param createdCourses Courses created by the user
     * @see User#createdCourses
     */
    public void setCreatedCourses(Set<Course> createdCourses) {
        this.createdCourses = createdCourses;
    }

    /**
     * Getter
     *
     * @return User roles
     * @see User#roles
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Getter
     *
     * @return User cart
     * @see User#cart
     */
    public Cart getCart() {
        return cart;
    }

    /**
     * Getter
     *
     * @return User token
     * @see User#token
     */
    public Token getToken() {
        return token;
    }

    /**
     * Setter
     *
     * @param token User token
     * @see User#token
     */
    public void setToken(Token token) {
        this.token = token;
    }

    /**
     * Getter
     *
     * @return User roles
     * @see User#roles
     */
    @Schema(
            description = "List of authorities granted to user",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    /**
     * Getter
     *
     * @return User password
     * @see User#password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Getter
     *
     * @return User nickname
     * @see User#username
     */
    @Override
    public String getUsername() {
        return username;
    }

    @Schema(
            description = "True if user's account is not expired",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Schema(
            description = "True if user's account is not locked",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Schema(
            description = "True if user's credentials is not expired",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Schema(description = "True if user is enabled",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) &&
                Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password);
    }

    /**
     * Adding a role to a user
     *
     * @param role User role
     */
    public void addRole(Role role) {
        roles.add(role);
    }

    /**
     * Removing user roles
     *
     */
    public void clearRoles() {
        roles.clear();
    }
}
