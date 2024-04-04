package de.aittr.online_lessons.controllers.api;

import de.aittr.online_lessons.domain.dto.ChangePasswordDto;
import de.aittr.online_lessons.domain.dto.UserDto;
import de.aittr.online_lessons.exception_handling.exceptions.UserAlreadyExistsException;
import de.aittr.online_lessons.validation.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tags(
        @Tag(name = "User controller", description = "Controller for some operations with users")
)
@RequestMapping("/api/users")
public interface UserApi {
    @PostMapping("/register")
    @Operation(
            summary = "New User Registration",
            description = "Saving a new user passed in the body of the request to the database"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "User is registered",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserValidationErrorDto.class))),
            @ApiResponse(responseCode = "409",
                    description = "There is already a user with this email",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserAlreadyExistsErrorDto.class))),
    })
    @ResponseStatus(HttpStatus.CREATED)
    UserDto register(
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User DTO object")
            @RequestBody
            UserDto user
    );

    @PutMapping("/set_admin/{username}")
    @Operation(
            summary = "Adding an admin role to a user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User became an admin"),
            @ApiResponse(responseCode = "403",
                    description = "Access is denied",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ForbiddenErrorDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "User is not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserNotFoundErrorDto.class))),
    })
    void setRoleAdmin(
            @PathVariable
            @Parameter(description = "User nickname")
            String username

    );

    @GetMapping("/account_info/{username}")
    @Operation(
            summary = "Getting information about a user's account"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Getting user info",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "403",
                    description = "Access is denied",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ForbiddenErrorDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "User is not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserNotFoundErrorDto.class))),
    })
    UserDto getUserInfo(
            @PathVariable
            @Parameter(description = "User nickname")
            String username
    );

    @PutMapping("/change_password/{username}")
    @Operation(
            summary = "Changing user password"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The user's password has changed"),
            @ApiResponse(responseCode = "400",
                    description = "Incorrect password fields",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PasswordMismatchErrorDto.class))),
            @ApiResponse(responseCode = "403",
                    description = "Access is denied",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ForbiddenErrorDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "User is not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserNotFoundErrorDto.class))),
    })
    void changePassword(
            @PathVariable
            @Parameter(description = "User nickname")
            String username,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "DTO object for changing password")
            @RequestBody
            ChangePasswordDto dto
    );

    @DeleteMapping("/delete/{username}")
    @Operation(
            summary = "Deleting a user",
            description = "Removing a user from the database using the nickname passed in the query string"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User has been deleted"),
            @ApiResponse(responseCode = "403",
                    description = "Access is denied",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ForbiddenErrorDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "User is not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserNotFoundErrorDto.class))),
    })
    void deleteUser(
            @PathVariable
            @Parameter(description = "User nickname")
            String username
    );
}
