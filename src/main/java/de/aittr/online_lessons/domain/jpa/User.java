package de.aittr.online_lessons.domain.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
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

@Schema(description = "User entity")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Schema(description = "User unique identifier", example = "11")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Schema(description = "Username that use for logging in", example = "Stas9n")
    @Column(name = "username")
    private String username;

    @Schema(description = "User's email", example = "john@gmail.com")
    @Column(name = "email")
    private String email;

    @Schema(description = "User's raw password for logging in", example = "Qwerty123$")
    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @JsonIgnore
    private Set<Enrollment> enrollments = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Course> createdCourses = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToOne(mappedBy = "user")
    private Cart cart;

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Set<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public Set<Course> getCreatedCourses() {
        return createdCourses;
    }

    public void setCreatedCourses(Set<Course> createdCourses) {
        this.createdCourses = createdCourses;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Cart getCart() {
        return cart;
    }

    @Schema(
            description = "List of authorities granted to user",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

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
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, roles);
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void clearRoles() {
        roles.clear();
    }
}
