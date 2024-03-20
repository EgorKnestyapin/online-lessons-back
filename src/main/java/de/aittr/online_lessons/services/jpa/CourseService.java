package de.aittr.online_lessons.services.jpa;

import de.aittr.online_lessons.domain.dto.CourseDto;
import de.aittr.online_lessons.domain.jpa.Course;
import de.aittr.online_lessons.repositories.jpa.CourseRepository;
import de.aittr.online_lessons.services.interfaces.ICourseService;
import de.aittr.online_lessons.services.mapping.CourseMappingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService implements ICourseService {
    private final CourseRepository repository;

    private final CourseMappingService mappingService;

    public CourseService(CourseRepository repository, CourseMappingService mappingService) {
        this.repository = repository;
        this.mappingService = mappingService;
    }

    @Override
    public CourseDto save(CourseDto courseDto) {
        Course course = mappingService.mapDtoToEntity(courseDto);
        course.setId(0);
        try {
            course = repository.save(course);
        } catch (Exception e) {
            throw new RuntimeException("Incorrect values of course fields");
        }
        return mappingService.mapEntityToDto(course);
    }

    @Override
    public List<CourseDto> getAllCourses() {
        return repository.findAll().stream()
                .map(mappingService::mapEntityToDto)
                .toList();
    }

    @Override
    public CourseDto getCourseById(int id) {
        Course course = repository.findById(id).orElse(null);
        if (course != null) {
            return mappingService.mapEntityToDto(course);
        }
        return null;
    }

    @Override
    public void update(CourseDto courseDto) {

    }

    @Override
    public void deleteById(int id) {

    }
}
