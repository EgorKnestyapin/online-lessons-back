package de.aittr.online_lessons.controllers.api;

import de.aittr.online_lessons.domain.dto.CourseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tags(
        @Tag(name = "Courses")
)
@RequestMapping("/api/courses")
public interface CourseApi {


    @PostMapping
    @Operation(
            summary = "Создание курса",
            description = "Сохранение в базу данных нового курса, переданного в теле запроса"
    )
    CourseDto createCourse(
            @Valid
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Объект ДТО курса")
            CourseDto courseDto
    );

    @GetMapping
    @Operation(
            summary = "Получение всех курсов",
            description = "Получение списка всех объектов курсов, хранящихся в базе данных"
    )
    List<CourseDto> getAll();

    @Operation(
            summary = "Получение конкретного курса по идентификатору",
            description = "Получение объекта курса, соответствующего переданному идентификатору"
    )
    @GetMapping("/{id}")
    CourseDto getById(
            @PathVariable
            @Parameter(description = "Идентификатор курса")
            int id
    );

    @Operation(
            summary = "Обновление курса",
            description = "Обновление данных курса с заданным ID"
    )
    @PatchMapping("/{id}")
    CourseDto updateCourse(
            @PathVariable int id,
            @Valid
            @RequestBody CourseDto courseDto);

    @Operation(
            summary = "Удаление курса",
            description = "Удаление курса с заданным ID"
    )
    @DeleteMapping("/{id}")
    void deleteCourse(@PathVariable int id);
}

