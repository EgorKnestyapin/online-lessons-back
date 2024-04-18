package de.aittr.online_lessons.security.sec_service;

import de.aittr.online_lessons.domain.dto.UserDto;
import de.aittr.online_lessons.domain.jpa.User;
import de.aittr.online_lessons.exception_handling.exceptions.RefreshTokenValidationException;
import de.aittr.online_lessons.exception_handling.exceptions.UserNotAuthenticated;
import de.aittr.online_lessons.security.sec_dto.TokenResponseDto;
import de.aittr.online_lessons.security.sec_dto.UserLoginDto;
import de.aittr.online_lessons.services.UserService;
import jakarta.security.auth.message.AuthException;
import jakarta.transaction.Transactional;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@TestPropertySource(locations = "classpath:test.properties")
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void loginPositiveTest() throws AuthException {
        UserLoginDto userLoginDto = new UserLoginDto("user123@example.com", "Testtest1!");
        TokenResponseDto login = authService.login(userLoginDto);

        Assert.assertTrue(tokenService.validateRefreshToken(login.getRefreshToken()));
        Assert.assertTrue(tokenService.validateAccessToken(login.getAccessToken()));
        Assert.assertNull(login.getMessage());
    }

    @Test
    void loginIncorrectEmailNegativeTest() {
        UserLoginDto userLoginDto = new UserLoginDto("user@example.com", "Testtest1!");
        Exception exception = assertThrows(AuthException.class, () -> {
            authService.login(userLoginDto);
        });
        String expectedMessage = "Email or password is incorrect";
        String actualMessage = exception.getMessage();

        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void loginIncorrectPasswordNegativeTest() {
        UserLoginDto userLoginDto = new UserLoginDto("user123@example.com", "Testtes1!");
        Exception exception = assertThrows(AuthException.class, () -> {
            authService.login(userLoginDto);
        });
        String expectedMessage = "Email or password is incorrect";
        String actualMessage = exception.getMessage();

        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void getAccessTokenPositiveTest() {
        User user = (User) userService.loadUserByUsername("user123");
        String refreshToken = tokenService.generateRefreshToken(user);
        tokenService.saveRefreshToken(refreshToken, user);
        TokenResponseDto actual = authService.getAccessToken(refreshToken);

        Assert.assertTrue(tokenService.validateAccessToken(actual.getAccessToken()));
        Assert.assertNull(actual.getMessage());
        Assert.assertNull(actual.getRefreshToken());
    }

    @Test
    void getAccessTokenInvalidRefreshNegativeTest() {
        Exception exception = assertThrows(RefreshTokenValidationException.class, () -> {
            authService.getAccessToken("123");
        });
        String expectedMessage = "Refresh token is not valid";
        String actualMessage = exception.getMessage();

        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void getAccessTokenUserNeverLoginNegativeTest() {
        UserDto userDto = new UserDto(0, "michael", "michael322@gmail.com",
                "qwerty123", null);
        userService.register(userDto);
        User user = (User) userService.loadUserByUsername("michael");
        String refreshToken = tokenService.generateRefreshToken(user);
        Exception exception = assertThrows(UserNotAuthenticated.class, () -> {
            authService.getAccessToken(refreshToken);
        });
        String expectedMessage = "User has never logged in";
        String actualMessage = exception.getMessage();

        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void getAuthInfo() {}
}