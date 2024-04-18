package de.aittr.online_lessons.repositories.jpa;

import de.aittr.online_lessons.domain.jpa.Course;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Jpa repository for working with course entity {@link Course}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
public interface CourseRepository extends JpaRepository<Course, Integer> {
}
