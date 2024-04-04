package de.aittr.online_lessons.controllers.api;

import de.aittr.online_lessons.domain.dto.CourseDto;
import de.aittr.online_lessons.domain.dto.EnrollmentResponseDto;
import de.aittr.online_lessons.validation.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@Tags(
        @Tag(name = "Course controller", description = "Controller for some operations with courses")
)
@RequestMapping("/api/courses")
public interface CourseApi {


    @PostMapping("/{username}")
    @Operation(
            summary = "Creating a course",
            description = "Saving a new course passed in the body of the request to the database"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Course was successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Incorrect course fields",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseValidationErrorDto.class))),
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
    CourseDto createCourse(
            @Valid
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Course DTO object")
            CourseDto courseDto,
            @PathVariable String username
    );

    @GetMapping
    @Operation(
            summary = "Getting all courses",
            description = "Getting a list of all course objects stored in the database"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Getting a list of courses",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = CourseDto.class)))),
    })
    @ResponseStatus(HttpStatus.CREATED)
    List<CourseDto> getAll();

    @GetMapping("/{id}")
    @Operation(
            summary = "Getting a specific course by identifier",
            description = "Getting the course object corresponding to the passed ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Course received",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "Course not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseNotFoundErrorDto.class))),
    })
    @ResponseStatus(HttpStatus.CREATED)
    CourseDto getById(
            @PathVariable
            @Parameter(description = "Course ID")
            int id
    );

    @PutMapping("/{id}")
    @Operation(
            summary = "Course update",
            description = "Updating course data with a given ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Course was successfully updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Incorrect course fields",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseValidationErrorDto.class))),
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
    CourseDto updateCourse(
            @PathVariable int id,
            @Valid
            @RequestBody CourseDto courseDto);

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deleting a course",
            description = "Deleting a course with a given ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Course was successfully deleted",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseDto.class))),
            @ApiResponse(responseCode = "403",
                    description = "Access is denied",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ForbiddenErrorDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "Course not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseNotFoundErrorDto.class))),
    })
    void deleteCourse(@PathVariable int id);

    @GetMapping("/available/{username}")
    @Operation(
            summary = "Getting the courses available to the user",
            description = "Getting a list of courses from the database available to a specific user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Getting available courses",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = EnrollmentResponseDto.class)))),
            @ApiResponse(responseCode = "403",
                    description = "Access is denied",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ForbiddenErrorDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserNotFoundErrorDto.class))),
    })
    @ResponseStatus(HttpStatus.CREATED)
    Set<EnrollmentResponseDto> getAvailableCourses(
            @PathVariable
            @Parameter(description = "User nickname")
            String username
    );

    @GetMapping("/created/{username}")
    @Operation(
            summary = "Getting user-created courses",
            description = "Getting a list of courses from the database created by a specific user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Getting available courses",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = CourseDto.class)))),
            @ApiResponse(responseCode = "403",
                    description = "Access is denied",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ForbiddenErrorDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserNotFoundErrorDto.class))),
    })
    @ResponseStatus(HttpStatus.CREATED)
    Set<CourseDto> getCreatedCourses(
            @PathVariable
            @Parameter(description = "User nickname")
            String username
    );
}

