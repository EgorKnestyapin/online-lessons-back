package de.aittr.online_lessons.repositories.jpa;

import de.aittr.online_lessons.domain.jpa.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
