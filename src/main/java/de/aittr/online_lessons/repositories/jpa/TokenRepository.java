package de.aittr.online_lessons.repositories.jpa;

import de.aittr.online_lessons.domain.jpa.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Integer> {
}
