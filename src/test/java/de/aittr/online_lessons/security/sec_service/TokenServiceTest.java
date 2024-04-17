package de.aittr.online_lessons.security.sec_service;

import de.aittr.online_lessons.domain.jpa.Token;
import de.aittr.online_lessons.domain.jpa.User;
import de.aittr.online_lessons.security.sec_dto.AuthInfo;
import de.aittr.online_lessons.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
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

import javax.crypto.SecretKey;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@TestPropertySource(locations = "classpath:application.properties")
class TokenServiceTest {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    SecretKey refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode("kngfGE1O50ODt/0AqMsw8lrFUseDl5pK33aJUSUIvpU="));
    SecretKey accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode("EF0F9GzSk2DUz2SeOKSdFYcN7R0TQHHdvev/jPmT6bA="));

    @BeforeEach
    void setUp() {
    }

    @Test
    void generateAccessToken() {
        User user = (User) userService.loadUserByUsername("user123");
        String accessToken = tokenService.generateAccessToken(user);

        Assert.assertTrue(tokenService.validateAccessToken(accessToken));
    }

    @Test
    void generateRefreshToken() {
        User user = (User) userService.loadUserByUsername("user123");
        String refreshToken = tokenService.generateRefreshToken(user);

        Assert.assertTrue(tokenService.validateRefreshToken(refreshToken));
    }

    @Test
    void validateAccessTokenPositiveTest() {
        User user = (User) userService.loadUserByUsername("user123");
        String accessToken = tokenService.generateAccessToken(user);

        Assert.assertTrue(tokenService.validateAccessToken(accessToken));
    }

    @Test
    void validateAccessTokenNegativeTest() {
        String invalidAccessToken = "hello123";

        Assert.assertFalse(tokenService.validateAccessToken(invalidAccessToken));
    }

    @Test
    void validateRefreshTokenPositiveTest() {
        User user = (User) userService.loadUserByUsername("user123");
        String refreshToken = tokenService.generateRefreshToken(user);

        Assert.assertTrue(tokenService.validateRefreshToken(refreshToken));
    }

    @Test
    void validateRefreshTokenNegativeTest() {
        String invalidRefreshToken = "hello123";

        Assert.assertFalse(tokenService.validateRefreshToken(invalidRefreshToken));
    }

    @Test
    void saveRefreshToken() {
        User user = (User) userService.loadUserByUsername("user123");
        String refreshToken = tokenService.generateRefreshToken(user);
        Token expected = new Token(3, refreshToken, user);
        Token actual = tokenService.saveRefreshToken(refreshToken, user);

        Assert.assertEquals(expected.getRefreshToken(), actual.getRefreshToken());
        Assert.assertEquals(expected.getUser(), actual.getUser());
    }

    @Test
    void getAccessClaims() {
        User user = (User) userService.loadUserByUsername("user123");
        String token = tokenService.generateAccessToken(user);
        Claims expected = Jwts.parser()
                .verifyWith(accessKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        Claims actual = tokenService.getAccessClaims(token);

        Assert.assertEquals(expected, actual);
    }

    @Test
    void getRefreshClaims() {
        User user = (User) userService.loadUserByUsername("user123");
        String token = tokenService.generateRefreshToken(user);
        Claims expected = Jwts.parser()
                .verifyWith(refreshKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        Claims actual = tokenService.getRefreshClaims(token);

        Assert.assertEquals(expected, actual);
    }

    @Test
    void generateAuthInfo() {
        User user = (User) userService.loadUserByUsername("user123");
        String accessToken = tokenService.generateAccessToken(user);
        Claims accessClaims = tokenService.getAccessClaims(accessToken);
        AuthInfo authInfo = tokenService.generateAuthInfo(accessClaims);

        Assert.assertEquals("user123", authInfo.getName());
        Assert.assertEquals(1, authInfo.getCartId());
    }
}