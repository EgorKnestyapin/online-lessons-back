package de.aittr.online_lessons.repositories.jpa;

import de.aittr.online_lessons.domain.jpa.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Jpa repository for working with enrollment entity {@link Enrollment}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {

    List<Enrollment> findByCourseId(int courseId);
}
