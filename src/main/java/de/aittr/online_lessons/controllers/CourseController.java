package de.aittr.online_lessons.controllers;

import de.aittr.online_lessons.controllers.api.CourseApi;
import de.aittr.online_lessons.domain.dto.CourseDto;
import de.aittr.online_lessons.domain.dto.EnrollmentResponseDto;
import de.aittr.online_lessons.services.CourseService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * Controller that accepts requests related to course {@link CourseService}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@RestController
public class CourseController implements CourseApi {

    /**
     * {@link CourseService}
     */
    private final CourseService service;

    /**
     * Constructor for creating course controller
     *
     * @param service Course service
     */
    public CourseController(CourseService service) {
        this.service = service;
    }

    /**
     * Creating new course
     *
     * @param courseDto Course DTO
     * @param username  User nickname
     * @return Created course
     */
    @Override
    public CourseDto createCourse(CourseDto courseDto, String username) {
        return service.save(courseDto, username);
    }

    /**
     * Getting all courses
     *
     * @return List of courses
     */
    @Override
    public List<CourseDto> getAll() {
        return service.getAllCourses();
    }

    /**
     * Getting a course
     *
     * @param id Course ID
     * @return Course
     */
    @Override
    public CourseDto getById(int id) {
        return service.getCourseById(id);
    }

    /**
     * Updating a course
     *
     * @param id        Course ID
     * @param courseDto Course DTO
     * @return Updated course
     */
    @Override
    public CourseDto updateCourse(int id, CourseDto courseDto) {
        return service.update(id, courseDto);
    }

    /**
     * Deleting a course
     *
     * @param id Course ID
     */
    @Override
    public void deleteCourse(int id) {
        service.deleteById(id);
    }

    /**
     * Getting available courses
     *
     * @param username User nickname
     * @return Set of enrollments
     */
    @Override
    public Set<EnrollmentResponseDto> getAvailableCourses(String username) {
        return service.getEnrollmentsByUsername(username);
    }

    /**
     * Getting created courses
     *
     * @param username User nickname
     * @return Set of courses
     */
    @Override
    public Set<CourseDto> getCreatedCourses(String username) {
        return service.getCreatedCoursesByUsername(username);
    }
}
