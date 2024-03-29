package de.aittr.online_lessons.controllers.api;

import de.aittr.online_lessons.domain.dto.CourseDto;
import de.aittr.online_lessons.domain.dto.EnrollmentDto;
import de.aittr.online_lessons.domain.jpa.Enrollment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@Tags(
        @Tag(name = "Courses")
)
@RequestMapping("/api/courses")
public interface CourseApi {


    @PostMapping("/{username}")
    @Operation(
            summary = "Создание курса",
            description = "Сохранение в базу данных нового курса, переданного в теле запроса"
    )
    CourseDto createCourse(
            @Valid
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Объект ДТО курса")
            CourseDto courseDto,
            @PathVariable String username
    );

    @GetMapping
    @Operation(
            summary = "Получение всех курсов",
            description = "Получение списка всех объектов курсов, хранящихся в базе данных"
    )
    List<CourseDto> getAll();

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение конкретного курса по идентификатору",
            description = "Получение объекта курса, соответствующего переданному идентификатору"
    )
    CourseDto getById(
            @PathVariable
            @Parameter(description = "Идентификатор курса")
            int id
    );

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновление курса",
            description = "Обновление данных курса с заданным ID"
    )
    CourseDto updateCourse(
            @PathVariable int id,
            @Valid
            @RequestBody CourseDto courseDto);

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление курса",
            description = "Удаление курса с заданным ID"
    )
    void deleteCourse(@PathVariable int id);

    @GetMapping("/available/{username}")
    @Operation(
            summary = "Получение курсов, доступных пользователю",
            description = "Получение списка курсов из базы данных, доступные конкретному пользователю"
    )
    Set<EnrollmentDto> getAvailableCourses(
            @PathVariable
            @Parameter(description = "Никнейм пользователя")
            String username
    );

    @GetMapping("/created/{username}")
    @Operation(
            summary = "Получение курсов, созданных пользователем",
            description = "Получение списка курсов из базы данных, созданные конкретным пользователем"
    )
    Set<CourseDto> getCreatedCourses(
            @PathVariable
            @Parameter(description = "Никнейм пользователя")
            String username
    );
}

