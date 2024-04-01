package de.aittr.online_lessons.domain.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "refresh_token")
    private String refreshToken;

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
