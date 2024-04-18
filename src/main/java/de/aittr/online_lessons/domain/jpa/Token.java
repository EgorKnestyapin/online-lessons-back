package de.aittr.online_lessons.domain.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;

/**
 * Class describing the token.
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "token")
public class Token {

    /**
     * Token ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Refresh token
     */
    @Column(name = "refresh_token")
    @NotNull
    private String refreshToken;

    /**
     * User who owns the token
     */
    @OneToOne
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(id, token.id) && Objects.equals(refreshToken, token.refreshToken) && Objects.equals(user, token.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, refreshToken, user);
    }
}
