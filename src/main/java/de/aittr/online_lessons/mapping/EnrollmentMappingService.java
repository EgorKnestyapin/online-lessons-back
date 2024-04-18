package de.aittr.online_lessons.mapping;

import de.aittr.online_lessons.domain.dto.EnrollmentResponseDto;
import de.aittr.online_lessons.domain.jpa.Enrollment;
import org.mapstruct.Mapper;

import java.util.Set;

/**
 * Service containing tools for enrollment mapping {@link Enrollment}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@Mapper(componentModel = "spring")
public interface EnrollmentMappingService {

    Set<EnrollmentResponseDto> mapSetEntityToSetDto(Set<Enrollment> enrollments);

    Set<Enrollment> mapSetDtoToSetEntity(Set<EnrollmentResponseDto> enrollments);
}
