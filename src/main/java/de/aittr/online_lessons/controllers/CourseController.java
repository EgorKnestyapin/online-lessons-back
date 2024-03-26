package de.aittr.online_lessons.controllers;

import de.aittr.online_lessons.controllers.api.CourseApi;
import de.aittr.online_lessons.domain.dto.CourseDto;
import de.aittr.online_lessons.services.jpa.CourseService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseController implements CourseApi {

    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    @Override
    public CourseDto createCourse(CourseDto courseDto) {
        return service.save(courseDto);
    }

    @Override
    public List<CourseDto> getAll() {
        return service.getAllCourses();
    }

    @Override
    public CourseDto getById(int id) {
        return service.getCourseById(id);
    }

    @Override
    public CourseDto updateCourse(int id, CourseDto courseDto) {
        return service.update(id, courseDto);
    }

    @Override
    public void deleteCourse(int id) {
        service.deleteById(id);
    }
}
