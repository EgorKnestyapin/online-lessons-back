package de.aittr.online_lessons.controllers;

import de.aittr.online_lessons.controllers.api.AuthApi;
import de.aittr.online_lessons.security.sec_dto.AuthInfo;
import de.aittr.online_lessons.security.sec_dto.RefreshRequestDto;
import de.aittr.online_lessons.security.sec_dto.TokenResponseDto;
import de.aittr.online_lessons.security.sec_dto.UserLoginDto;
import de.aittr.online_lessons.security.sec_service.AuthService;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that accepts requests related to authorization {@link AuthService}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@RestController
public class AuthController implements AuthApi {
    /**
     * {@link AuthService}
     */
    private AuthService service;

    /**
     * Constructor for creating authorization controller
     *
     * @param service Authorization service
     */
    public AuthController(AuthService service) {
        this.service = service;
    }

    /**
     * User login
     *
     * @param dto      User DTO for login
     * @param response HTTP response
     * @return Tokens
     */
    @Override
    public ResponseEntity<TokenResponseDto> login(UserLoginDto dto, HttpServletResponse response) {
        try {
            TokenResponseDto tokenDto = service.login(dto);

            Cookie cookie = new Cookie("Access-Token", tokenDto.getAccessToken());
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);

            return ResponseEntity.ok(tokenDto);
        } catch (AuthException e) {
            TokenResponseDto tokenDto = new TokenResponseDto(e.getMessage());
            return new ResponseEntity<>(tokenDto, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Getting new access token
     *
     * @param request Refresh token
     * @return Access token
     */
    @Override
    public ResponseEntity<TokenResponseDto> getNewAccessToken(RefreshRequestDto request) {
        TokenResponseDto accessToken = service.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(accessToken);
    }

    /**
     * Logout from the system
     *
     * @param response HTTP response
     */
    @Override
    public void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("Access-Token", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    @Override
    public AuthInfo getAuthInfo() {
        return service.getAuthInfo();
    }
}
