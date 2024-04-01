package de.aittr.online_lessons.controllers;

import de.aittr.online_lessons.controllers.api.AuthApi;
import de.aittr.online_lessons.security.sec_dto.RefreshRequestDto;
import de.aittr.online_lessons.security.sec_dto.TokenResponseDto;
import de.aittr.online_lessons.security.sec_dto.UserLoginDto;
import de.aittr.online_lessons.security.sec_service.AuthService;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthApi {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

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

    @Override
    public ResponseEntity<TokenResponseDto> getNewAccessToken(RefreshRequestDto request) {
        TokenResponseDto accessToken = service.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(accessToken);
    }

    @Override
    public void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("Access-Token", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
