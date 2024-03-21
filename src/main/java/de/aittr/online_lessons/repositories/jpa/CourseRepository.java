package de.aittr.online_lessons.repositories.jpa;

import de.aittr.online_lessons.domain.jpa.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}
