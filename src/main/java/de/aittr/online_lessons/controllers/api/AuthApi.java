package de.aittr.online_lessons.controllers.api;

import de.aittr.online_lessons.security.sec_dto.AuthInfo;
import de.aittr.online_lessons.security.sec_dto.RefreshRequestDto;
import de.aittr.online_lessons.security.sec_dto.TokenResponseDto;
import de.aittr.online_lessons.security.sec_dto.UserLoginDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
    AuthInfo getAuthInfo();
}
