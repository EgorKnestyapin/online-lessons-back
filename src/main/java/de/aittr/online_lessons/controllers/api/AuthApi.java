package de.aittr.online_lessons.controllers.api;

import de.aittr.online_lessons.controllers.AuthController;
import de.aittr.online_lessons.security.sec_dto.AuthInfo;
import de.aittr.online_lessons.security.sec_dto.RefreshRequestDto;
import de.aittr.online_lessons.security.sec_dto.TokenResponseDto;
import de.aittr.online_lessons.security.sec_dto.UserLoginDto;
import de.aittr.online_lessons.validation.dto.RefreshTokenValidationErrorDto;
import de.aittr.online_lessons.validation.dto.UserNotAuthenticatedErrorDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * API for authorization controller {@link AuthController}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@Tags(
        @Tag(
                name = "Authorization controller",
                description = "Controller for security operations, login/logout, getting new tokens etc"
        )
)
@RequestMapping("/api/auth")
public interface AuthApi {

    @PostMapping("/login")
    @Operation(
            summary = "Login",
            description = "Logging into the system"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User logged in",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TokenResponseDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Login or password is incorrect",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TokenResponseDto.class))),
    })
    ResponseEntity<TokenResponseDto> login(
            @RequestBody @io.swagger.v3.oas.annotations.parameters
                    .RequestBody(description = "Object of an user that logging in") UserLoginDto dto,
            @Parameter(description = "Object of a response that will be transferred to a client")
            HttpServletResponse response
    );

    @PostMapping("/access")
    @Operation(
            summary = "Get new access token",
            description = "Receiving a new access token through providing an existing refresh token"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Getting access token",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TokenResponseDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Incorrect refresh token",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RefreshTokenValidationErrorDto.class))),
    })
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<TokenResponseDto> getNewAccessToken(
            @RequestBody @io.swagger.v3.oas.annotations.parameters
                    .RequestBody(description = "Object of an inbound request that contains a refresh token")
            RefreshRequestDto request
    );

    @GetMapping("/logout")
    @Operation(
            summary = "Logout",
            description = "Logging out from the system"
    )
    void logout(
            @Parameter(description = "Object of a response that will be transferred to a client")
            HttpServletResponse response
    );

    @GetMapping("/auth_info")
    @Operation(description = "Getting auth info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Information about the current authorized user was received",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthInfo.class))),
            @ApiResponse(responseCode = "401",
                    description = "User is not authorized in the system",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserNotAuthenticatedErrorDto.class))),
    })
    @ResponseStatus(HttpStatus.CREATED)
    AuthInfo getAuthInfo();
}
