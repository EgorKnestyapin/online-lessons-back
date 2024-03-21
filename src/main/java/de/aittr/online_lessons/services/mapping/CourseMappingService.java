package de.aittr.online_lessons.services.mapping;

import de.aittr.online_lessons.domain.dto.CourseDto;
import de.aittr.online_lessons.domain.jpa.Course;
import org.springframework.stereotype.Service;

@Service
public class CourseMappingService {

    public Course mapDtoToEntity(CourseDto courseDto) {
        int id = courseDto.getId();
        String name = courseDto.getName();
        String fileName = courseDto.getFileName();
        String description = courseDto.getDescription();
        return new Course(id, name, fileName, description);
    }

    public CourseDto mapEntityToDto(Course course) {
        int id = course.getId();
        String name = course.getName();
        String fileName = course.getFileName();
        String description = course.getDescription();
        return new CourseDto(id, name, fileName, description);
    }
}
