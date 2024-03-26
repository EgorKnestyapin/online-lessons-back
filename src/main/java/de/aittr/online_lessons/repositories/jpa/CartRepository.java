package de.aittr.online_lessons.repositories.jpa;

import de.aittr.online_lessons.domain.jpa.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
}
