package de.aittr.online_lessons.services;

import de.aittr.online_lessons.domain.dto.CourseDto;
import de.aittr.online_lessons.domain.jpa.Course;
import de.aittr.online_lessons.domain.jpa.Enrollment;
import de.aittr.online_lessons.domain.jpa.User;
import de.aittr.online_lessons.exception_handling.exceptions.CourseNotFoundException;
import de.aittr.online_lessons.exception_handling.exceptions.CourseValidationException;
import de.aittr.online_lessons.repositories.jpa.CourseRepository;
import de.aittr.online_lessons.mapping.CourseMappingService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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
    public CourseDto save(CourseDto courseDto, String username) {
        Course course = mappingService.mapDtoToEntity(courseDto);
        course.setId(0);
        User user = (User) userService.loadUserByUsername(username);
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

    public CourseDto getCourseById(int courseId) {
        Course course = getCourseEntityById(courseId);
        return mappingService.mapEntityToDto(course);
    }


    public Course getCourseEntityById(int id) {
        Course course = repository.findById(id).orElse(null);
        if (course == null) {
            throw new CourseNotFoundException("Course not found");
        }
        return course;
    }

    public CourseDto update(int id, CourseDto courseDto) {
        repository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id " + id));

        Course course = mappingService.mapDtoToEntity(courseDto);
        course.setId(id);

        course = repository.save(course);

        return mappingService.mapEntityToDto(course);
    }

    public void deleteById(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Course not found with id " + id);
        }

        repository.deleteById(id);
    }

    public Set<Enrollment> getEnrollmentsByUsername(String username) {
        User user = (User) userService.loadUserByUsername(username);
        return user.getEnrollments();
    }
}
