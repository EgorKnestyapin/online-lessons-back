package de.aittr.online_lessons.controllers;

import de.aittr.online_lessons.domain.dto.CourseDto;
import de.aittr.online_lessons.services.jpa.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(
            summary = "Создание курса",
            description = "Сохранение в базу данных нового курса, переданного в теле запроса"
    )
    public CourseDto createCourse(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Объект ДТО курса")
            CourseDto courseDto
    ) {
        return service.save(courseDto);
    }

    @GetMapping
    @Operation(
            summary = "Получение всех курсов",
            description = "Получение списка всех объектов курсов, хранящихся в базе данных"
    )
    public List<CourseDto> getAll() {
        return service.getAllCourses();
    }

    @Operation(
            summary = "Получение конкретного курса по идентификатору",
            description = "Получение объекта курса, соответствующего переданному идентификатору"
    )
    @GetMapping("/{id}")
    public CourseDto getById(
            @PathVariable
            @Parameter(description = "Идентификатор курса")
            int id
    ) {
        return service.getCourseById(id);
    }
}
