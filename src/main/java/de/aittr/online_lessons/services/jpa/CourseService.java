package de.aittr.online_lessons.services.jpa;

import de.aittr.online_lessons.domain.dto.CourseDto;
import de.aittr.online_lessons.domain.jpa.Course;
import de.aittr.online_lessons.repositories.jpa.CourseRepository;
import de.aittr.online_lessons.services.mapping.CourseMappingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService{
    private final CourseRepository repository;

    private final CourseMappingService mappingService;


    public CourseService(CourseRepository repository, CourseMappingService mappingService) {
        this.repository = repository;
        this.mappingService = mappingService;
    }

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

    public List<CourseDto> getAllCourses() {
        return repository.findAll().stream()
                .map(mappingService::mapEntityToDto)
                .toList();
    }

    public CourseDto getCourseById(int id) {
        Course course = repository.findById(id).orElse(null);
        if (course != null) {
            return mappingService.mapEntityToDto(course);
        }
        return null;
    }

    public CourseDto update(int id, CourseDto courseDto) {
        Course existingCourse = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id " + id));

        if (courseDto.getName() != null) {
            existingCourse.setName(courseDto.getName());
        }
        if (courseDto.getFileName() != null) {
            existingCourse.setFileName(courseDto.getFileName());
        }
        if (courseDto.getDescription() != null) {
            existingCourse.setDescription(courseDto.getDescription());
        }

        existingCourse = repository.save(existingCourse);

        return mappingService.mapEntityToDto(existingCourse);
    }

    public void deleteById(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Course not found with id " + id);
        }

        repository.deleteById(id);
    }
}
