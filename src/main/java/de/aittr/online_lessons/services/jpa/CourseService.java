package de.aittr.online_lessons.services.jpa;

import de.aittr.online_lessons.domain.dto.CourseDto;
import de.aittr.online_lessons.domain.jpa.Course;
import de.aittr.online_lessons.domain.jpa.User;
import de.aittr.online_lessons.exception_handling.exceptions.CourseValidationException;
import de.aittr.online_lessons.repositories.jpa.CourseRepository;
import de.aittr.online_lessons.services.mapping.CourseMappingService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository repository;

    private final CourseMappingService mappingService;

    private final UserService userService;

    public CourseService(CourseRepository repository, CourseMappingService mappingService, UserService userService) {
        this.repository = repository;
        this.mappingService = mappingService;
        this.userService = userService;
    }

    @Transactional
    public CourseDto save(CourseDto courseDto, int authorId) {
        Course course = mappingService.mapDtoToEntity(courseDto);
        course.setId(0);
        User user = userService.getUserById(authorId);
        course.setUser(user);

        try {
            course = repository.save(course);
        } catch (Exception e) {
            throw new CourseValidationException("Incorrect values of course fields", e);
        }
        return mappingService.mapEntityToDto(course);
    }

    public List<CourseDto> getAllCourses() {
        return repository.findAll().stream()
                .map(mappingService::mapEntityToDto)
                .toList();
    }

    public CourseDto getCourseById(int id) {
        Course course = getCourseEntityById(id);
        if (course != null) {
            return mappingService.mapEntityToDto(course);
        }
        return null;
    }


    public Course getCourseEntityById(int id) {
        return repository.findById(id).orElse(null);
    }

    public CourseDto update(int id, CourseDto courseDto) {
        Course existingCourse = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id " + id));

        if (courseDto.getTitle() != null) {
            existingCourse.setTitle(courseDto.getTitle());
        }
        if (courseDto.getPrice() != 0) {
            existingCourse.setPrice(courseDto.getPrice());
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
