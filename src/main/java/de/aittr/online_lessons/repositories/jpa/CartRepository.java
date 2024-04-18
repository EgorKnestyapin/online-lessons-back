package de.aittr.online_lessons.repositories.jpa;

import de.aittr.online_lessons.domain.jpa.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Jpa repository for working with cart entity {@link Cart}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
public interface CartRepository extends JpaRepository<Cart, Integer> {
}
