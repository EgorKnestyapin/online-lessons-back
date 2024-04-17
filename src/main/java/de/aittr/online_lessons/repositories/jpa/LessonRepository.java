package de.aittr.online_lessons.repositories.jpa;

import de.aittr.online_lessons.domain.jpa.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

/**
 * Jpa repository for working with lesson entity {@link Lesson}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    Set<Lesson> findByCourseId(int i);
}
