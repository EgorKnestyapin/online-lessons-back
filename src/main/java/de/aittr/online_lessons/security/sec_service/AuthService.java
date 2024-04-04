package de.aittr.online_lessons.security.sec_service;

import de.aittr.online_lessons.domain.jpa.Token;
import de.aittr.online_lessons.domain.jpa.User;
import de.aittr.online_lessons.exception_handling.exceptions.UserNotAuthenticated;
import de.aittr.online_lessons.repositories.jpa.UserRepository;
import de.aittr.online_lessons.security.sec_dto.AuthInfo;
import de.aittr.online_lessons.security.sec_dto.TokenResponseDto;
import de.aittr.online_lessons.security.sec_dto.UserLoginDto;
import de.aittr.online_lessons.services.UserService;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Nonnull;
import jakarta.security.auth.message.AuthException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private UserService userService;

    private UserRepository userRepository;

    private TokenService tokenService;

    private BCryptPasswordEncoder encoder;

    public AuthService(UserService userService, UserRepository userRepository, TokenService tokenService,
                       BCryptPasswordEncoder encoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.encoder = encoder;
    }

    @Transactional
    public TokenResponseDto login(@Nonnull UserLoginDto inboundUser) throws AuthException {
        String email = inboundUser.getEmail();
        User foundUser = userRepository.findByEmail(email);

        if (foundUser == null || !encoder.matches(inboundUser.getPassword(), foundUser.getPassword())) {
            throw new AuthException("Email or password is incorrect");
        }

        String accessToken = tokenService.generateAccessToken(foundUser);
        String refreshToken = tokenService.generateRefreshToken(foundUser);
        Token token = tokenService.saveRefreshToken(refreshToken, foundUser);
        foundUser.setToken(token);
        return new TokenResponseDto(accessToken, refreshToken);
    }

    public TokenResponseDto getAccessToken(@Nonnull String refreshToken) {
        if (tokenService.validateRefreshToken(refreshToken)) {
            Claims refreshClaims = tokenService.getRefreshClaims(refreshToken);
            String username = refreshClaims.getSubject();
            User user = (User) userService.loadUserByUsername(username);
            if (user.getToken() == null) {
                throw new UserNotAuthenticated("User has never logged in");
            }
            String savedRefreshToken = user.getToken().getRefreshToken();

            if (savedRefreshToken != null && savedRefreshToken.equals(refreshToken)) {
                String accessToken = tokenService.generateAccessToken(user);
                return new TokenResponseDto(accessToken, null);
            }
        }
        return new TokenResponseDto(null, null);
    }

    public AuthInfo getAuthInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal().equals("anonymousUser")) {
            throw new UserNotAuthenticated("User is not authenticated");
        }
        return (AuthInfo) auth;
    }
}
