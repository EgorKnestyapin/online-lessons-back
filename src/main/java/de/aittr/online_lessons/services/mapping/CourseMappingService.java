package de.aittr.online_lessons.services.mapping;

import de.aittr.online_lessons.domain.dto.CourseDto;
import de.aittr.online_lessons.domain.jpa.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMappingService {
    Course mapDtoToEntity(CourseDto dto);

    CourseDto mapEntityToDto(Course user);
}