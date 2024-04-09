package de.aittr.online_lessons.repositories.jpa;

import de.aittr.online_lessons.domain.jpa.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
}
