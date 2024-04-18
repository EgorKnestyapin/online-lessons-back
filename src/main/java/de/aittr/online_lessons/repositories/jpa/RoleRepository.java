package de.aittr.online_lessons.repositories.jpa;

import de.aittr.online_lessons.domain.jpa.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Jpa repository for working with role entity {@link Role}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
