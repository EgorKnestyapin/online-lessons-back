package de.aittr.online_lessons.repositories.jpa;

import de.aittr.online_lessons.domain.jpa.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
}
