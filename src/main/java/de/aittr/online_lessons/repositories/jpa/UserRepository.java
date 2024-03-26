package de.aittr.online_lessons.repositories.jpa;

import de.aittr.online_lessons.domain.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
