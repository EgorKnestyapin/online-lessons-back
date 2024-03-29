package de.aittr.online_lessons.mapping;

import de.aittr.online_lessons.domain.dto.EnrollmentDto;
import de.aittr.online_lessons.domain.jpa.Enrollment;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface EnrollmentMappingService {

    Set<EnrollmentDto> mapSetEntityToSetDto(Set<Enrollment> enrollments);
}
