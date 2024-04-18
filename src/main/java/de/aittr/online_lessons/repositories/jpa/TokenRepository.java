package de.aittr.online_lessons.repositories.jpa;

import de.aittr.online_lessons.domain.jpa.Token;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Jpa repository for working with token entity {@link Token}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
public interface TokenRepository extends JpaRepository<Token, Integer> {
}
