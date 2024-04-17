package de.aittr.online_lessons.repositories.jpa;

import de.aittr.online_lessons.domain.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Jpa repository for working with user entity {@link User}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    User findByEmail(String email);
}
