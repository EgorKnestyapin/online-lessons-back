package de.aittr.online_lessons.controllers.api;

import de.aittr.online_lessons.domain.dto.CourseDto;
import de.aittr.online_lessons.domain.dto.LessonDto;
import de.aittr.online_lessons.validation.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Tags(
        @Tag(name = "Lesson controller", description = "Controller for some operations with lessons")
)
@RequestMapping("/api/lessons")
public interface LessonApi {

    @PostMapping("/{courseId}")
    @Operation(
            summary = "Creating a lesson",
            description = "Saving a new lesson passed in the body of the request to the database"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Lesson was successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LessonDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Incorrect lesson fields",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LessonValidationErrorDto.class))),
            @ApiResponse(responseCode = "403",
                    description = "Access is denied",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ForbiddenErrorDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "Lesson not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LessonNotFoundErrorDto.class))),
    })
    @ResponseStatus(HttpStatus.CREATED)
    LessonDto createLesson(
            @Valid
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Lesson DTO object")
            LessonDto lessonDto,
            @PathVariable int courseId
    );

    @GetMapping
    @Operation(
            summary = "Getting all lessons",
            description = "Getting a list of all lesson objects stored in the database"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Getting a list of lessons",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = LessonDto.class)))),
            @ApiResponse(responseCode = "403",
                    description = "Access is denied",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ForbiddenErrorDto.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    List<LessonDto> getAll();

    @GetMapping("/{courseId}")
    @Operation(
            summary = "Getting lessons from a specific course",
            description = "Getting a list of lessons from the database belonging to a specific course"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Getting created lessons",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = LessonDto.class)))),
            @ApiResponse(responseCode = "403",
                    description = "Access is denied",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ForbiddenErrorDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "Course not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseNotFoundErrorDto.class))),
    })
    @ResponseStatus(HttpStatus.CREATED)
    Set<LessonDto> getCreatedLessons(
            @PathVariable
            @Parameter(description = "Course ID")
            int courseId
    );

    @GetMapping("/demo/{courseId}")
    @Operation(
            summary = "Getting demo lessons from a specific course",
            description = "Getting two lessons from the database belonging to a specific course"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Getting demo lessons",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = LessonDto.class)))),
            @ApiResponse(responseCode = "404",
                    description = "Course not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseNotFoundErrorDto.class))),
    })
    @ResponseStatus(HttpStatus.CREATED)
    Set<LessonDto> getDemoLessons(
            @PathVariable
            @Parameter(description = "Course ID")
            int courseId
    );

    @PutMapping("/{lessonId}")
    @Operation(
            summary = "Lesson update",
            description = "Updating lesson data with a given ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Lesson was successfully updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LessonDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Incorrect lesson fields",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LessonValidationErrorDto.class))),
            @ApiResponse(responseCode = "403",
                    description = "Access is denied",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ForbiddenErrorDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "Lesson not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LessonNotFoundErrorDto.class))),
    })
    @ResponseStatus(HttpStatus.CREATED)
    LessonDto updateCourse(
            @PathVariable int lessonId,
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Lesson DTO object")
            @RequestBody LessonDto lessonDto);

    @DeleteMapping("/{lessonId}")
    @Operation(
            summary = "Deleting a lesson",
            description = "Deleting a lesson with a given ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Lesson was successfully deleted",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LessonDto.class))),
            @ApiResponse(responseCode = "403",
                    description = "Access is denied",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ForbiddenErrorDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "Lesson not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LessonNotFoundErrorDto.class))),
    })
    void deleteCourse(@PathVariable int lessonId);
}
