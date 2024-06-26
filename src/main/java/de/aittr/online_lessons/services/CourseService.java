package de.aittr.online_lessons.services;

import de.aittr.online_lessons.domain.dto.CourseDto;
import de.aittr.online_lessons.domain.dto.EnrollmentResponseDto;
import de.aittr.online_lessons.domain.jpa.Cart;
import de.aittr.online_lessons.domain.jpa.Course;
import de.aittr.online_lessons.domain.jpa.User;
import de.aittr.online_lessons.exception_handling.exceptions.CourseNotFoundException;
import de.aittr.online_lessons.exception_handling.exceptions.CourseValidationException;
import de.aittr.online_lessons.mapping.CourseMappingService;
import de.aittr.online_lessons.mapping.EnrollmentMappingService;
import de.aittr.online_lessons.repositories.jpa.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Service containing tools for working with course entity {@link Course}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@Service
public class CourseService {

    /**
     * {@link CourseRepository}
     */
    private final CourseRepository repository;

    /**
     * {@link CourseMappingService}
     */
    private final CourseMappingService courseMappingService;

    /**
     * {@link EnrollmentMappingService}
     */
    private final EnrollmentMappingService enrollmentMappingService;

    /**
     * {@link UserService}
     */
    private final UserService userService;

    /**
     * Constructor for creating course service
     *
     * @param repository               Course repository
     * @param courseMappingService     Mapping service for course
     * @param enrollmentMappingService Mapping service for enrollment
     * @param userService              User service
     */
    public CourseService(CourseRepository repository, CourseMappingService courseMappingService, EnrollmentMappingService enrollmentMappingService, UserService userService) {
        this.repository = repository;
        this.courseMappingService = courseMappingService;
        this.enrollmentMappingService = enrollmentMappingService;
        this.userService = userService;
    }

    /**
     * Saving course to database
     *
     * @param courseDto Course DTO
     * @param username  User nickname
     * @return Saved course DTO
     * @throws CourseValidationException Incorrect values of course fields
     */
    @Transactional
    public CourseDto save(CourseDto courseDto, String username) {
        Course course = courseMappingService.mapDtoToEntity(courseDto);
        course.setId(0);
        course.setCounter(0);

        if (course.getOldPrice() == 0) {
            course.setOldPrice((int) (course.getPrice() * 1.2));
        }

        User user = (User) userService.loadUserByUsername(username);
        course.setUser(user);

        try {
            course = repository.save(course);
        } catch (Exception e) {
            throw new CourseValidationException("Incorrect values of course fields", e);
        }
        return courseMappingService.mapEntityToDto(course);
    }

    /**
     * Getting all courses from the database
     *
     * @return List of courses
     */
    public List<CourseDto> getAllCourses() {
        return repository.findAll().stream().map(courseMappingService::mapEntityToDto).toList();
    }

    /**
     * Getting course by ID from the database
     *
     * @param courseId Course ID
     * @return Course
     */
    public CourseDto getCourseById(int courseId) {
        Course course = getCourseEntityById(courseId);
        return courseMappingService.mapEntityToDto(course);
    }

    /**
     * Getting course entity by ID from the database
     *
     * @param id Course ID
     * @return Course entity
     * @throws CourseNotFoundException Course not found
     */
    public Course getCourseEntityById(int id) {
        Course course = repository.findById(id).orElse(null);
        if (course == null) {
            throw new CourseNotFoundException("Course not found with id " + id);
        }
        return course;
    }

    /**
     * Updating course fields
     *
     * @param id        Course ID
     * @param courseDto Course DTO
     * @return Updated course
     */
    public CourseDto update(int id, CourseDto courseDto) {
        Course foundCourse = repository.findById(id).orElseThrow(() -> new CourseNotFoundException("Course not found with id " + id));

        Course course = courseMappingService.mapDtoToEntity(courseDto);
        course.setId(id);
        course.setOldPrice(foundCourse.getPrice());
        course.setCounter(foundCourse.getCounter());
        course.setUser(foundCourse.getUser());

        try {
            course = repository.save(course);
        } catch (Exception e) {
            throw new CourseValidationException("Incorrect values of course fields", e);
        }

        return courseMappingService.mapEntityToDto(course);
    }

    /**
     * Deleting a course by ID
     *
     * @param id Course ID
     * @throws CourseNotFoundException Course not found
     */
    @Transactional
    public void deleteById(int id) {
        if (!repository.existsById(id)) {
            throw new CourseNotFoundException("Course not found with id " + id);
        }
        Course foundCourse = getCourseEntityById(id);
        List<Cart> carts = new ArrayList<>(foundCourse.getCarts());
        carts.forEach(cart -> {
            cart.removeCourse(foundCourse);
        });
        repository.deleteById(id);
    }

    /**
     * Getting enrollments related to the course from the database
     *
     * @param username User nickname
     * @return Set of enrollments
     */
    public Set<EnrollmentResponseDto> getEnrollmentsByUsername(String username) {
        User user = (User) userService.loadUserByUsername(username);
        return enrollmentMappingService.mapSetEntityToSetDto(user.getEnrollments());
    }

    /**
     * Getting courses created by the user
     *
     * @param username User nickname
     * @return Set of created courses
     */
    public Set<CourseDto> getCreatedCoursesByUsername(String username) {
        User user = (User) userService.loadUserByUsername(username);
        return courseMappingService.mapSetEntityToSetDto(user.getCreatedCourses());
    }
}
