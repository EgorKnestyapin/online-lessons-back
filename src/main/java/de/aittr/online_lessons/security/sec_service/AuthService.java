package de.aittr.online_lessons.security.sec_service;

import de.aittr.online_lessons.domain.jpa.Token;
import de.aittr.online_lessons.domain.jpa.User;
import de.aittr.online_lessons.exception_handling.exceptions.RefreshTokenValidationException;
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

/**
 * Service containing tools for working with authorization information and tokens
 * {@link AuthInfo} {@link TokenResponseDto}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@Service
public class AuthService {

    /**
     * {@link UserService}
     */
    private UserService userService;

    /**
     * {@link UserRepository}
     */
    private UserRepository userRepository;

    /**
     * {@link TokenService}
     */
    private TokenService tokenService;

    /**
     * Encoder
     */
    private BCryptPasswordEncoder encoder;

    /**
     * Constructor for creating authorization service
     *
     * @param userService    User service
     * @param userRepository User repository
     * @param tokenService   Token service
     * @param encoder        Encoder
     */
    public AuthService(UserService userService, UserRepository userRepository, TokenService tokenService,
                       BCryptPasswordEncoder encoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.encoder = encoder;
    }

    /**
     * User login to receive tokens
     *
     * @param inboundUser User DTO for login
     * @return Access token and refresh token
     * @throws AuthException Incorrect fields in user DTO
     */
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

    /**
     * Getting access token
     *
     * @param refreshToken Refresh token
     * @return Access token
     * @throws UserNotAuthenticated            User has never logged in
     * @throws RefreshTokenValidationException Refresh token is not valid
     */
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
        throw new RefreshTokenValidationException("Refresh token is not valid");
    }

    /**
     * Getting information about the current authorized user
     *
     * @return Authorization information
     * @throws UserNotAuthenticated User is not authenticated
     */
    public AuthInfo getAuthInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal().equals("anonymousUser")) {
            throw new UserNotAuthenticated("User is not authenticated");
        }
        return (AuthInfo) auth;
    }
}
