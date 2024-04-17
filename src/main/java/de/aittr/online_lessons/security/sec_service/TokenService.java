package de.aittr.online_lessons.security.sec_service;

import de.aittr.online_lessons.domain.jpa.Role;
import de.aittr.online_lessons.domain.jpa.Token;
import de.aittr.online_lessons.domain.jpa.User;
import de.aittr.online_lessons.repositories.jpa.RoleRepository;
import de.aittr.online_lessons.repositories.jpa.TokenRepository;
import de.aittr.online_lessons.security.sec_dto.AuthInfo;
import de.aittr.online_lessons.security.sec_dto.TokenResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * Service containing tools for working with  tokens {@link TokenResponseDto}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@Service
public class TokenService {

    /**
     * Access token secret key
     */
    private SecretKey accessKey;

    /**
     * Refresh token secret key
     */
    private SecretKey refreshKey;

    /**
     * {@link RoleRepository}
     */
    private RoleRepository roleRepository;

    /**
     * {@link TokenRepository}
     */
    private TokenRepository tokenRepository;

    /**
     * Constructor for creating token service
     *
     * @param accessKey       Access token secret key
     * @param refreshKey      Refresh token secret key
     * @param roleRepository  Role repository
     * @param tokenRepository Token repository
     */
    public TokenService(
            @Value("${access.key}") String accessKey,
            @Value("${refresh.key}") String refreshKey,
            RoleRepository roleRepository, TokenRepository tokenRepository
    ) {
        this.accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessKey));
        this.refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshKey));
        this.roleRepository = roleRepository;
        this.tokenRepository = tokenRepository;
    }

    /**
     * Access token creation
     *
     * @param user User
     * @return Access token
     */
    public String generateAccessToken(@Nonnull User user) {
        LocalDateTime currentDate = LocalDateTime.now();
        Instant expirationInstant = currentDate.plusDays(7).atZone(ZoneId.systemDefault()).toInstant();
        Date expirationDate = Date.from(expirationInstant);

        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(expirationDate)
                .signWith(accessKey)
                .claim("roles", user.getAuthorities())
                .claim("name", user.getUsername())
                .claim("cartId", user.getCart().getId())
                .compact();
    }

    /**
     * Refresh token creation
     *
     * @param user User
     * @return Refresh token
     */
    public String generateRefreshToken(@Nonnull User user) {
        LocalDateTime currentDate = LocalDateTime.now();
        Instant expirationInstant = currentDate.plusDays(30).atZone(ZoneId.systemDefault()).toInstant();
        Date expirationDate = Date.from(expirationInstant);

        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(expirationDate)
                .signWith(refreshKey)
                .compact();
    }

    /**
     * Checking the validity of the access token
     *
     * @param accessToken Access token
     * @return Information whether the token is valid
     */
    public boolean validateAccessToken(@Nonnull String accessToken) {
        return validateToken(accessToken, accessKey);
    }

    /**
     * Checking the validity of the refresh token
     *
     * @param refreshToken Refresh token
     * @return Information whether the token is valid
     */
    public boolean validateRefreshToken(@Nonnull String refreshToken) {
        return validateToken(refreshToken, refreshKey);
    }

    /**
     * Checking the validity of the token
     *
     * @param token Token
     * @param key   Secret key
     * @return Information whether the token is valid
     */
    private boolean validateToken(@Nonnull String token, @Nonnull SecretKey key) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Saving the refresh token to the database
     *
     * @param refreshToken Refresh token
     * @param user         User
     * @return Saved refresh token
     */
    public Token saveRefreshToken(String refreshToken, User user) {
        int id = 0;
        if (user.getToken() != null) {
            id = user.getToken().getId();
        }
        Token newToken = new Token(id, refreshToken, user);
        return tokenRepository.save(newToken);
    }

    /**
     * Getting access token claims
     *
     * @param accessToken Access token
     * @return Access token claims
     */
    public Claims getAccessClaims(@Nonnull String accessToken) {
        return getClaims(accessToken, accessKey);
    }

    /**
     * Getting refresh token claims
     *
     * @param refreshToken Refresh token
     * @return Refresh token claims
     */
    public Claims getRefreshClaims(@Nonnull String refreshToken) {
        return getClaims(refreshToken, refreshKey);
    }

    /**
     * Getting token claims
     *
     * @param token Token
     * @param key   Secret key
     * @return Token claims
     */
    private Claims getClaims(@Nonnull String token, @Nonnull SecretKey key) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Authorization information creation
     *
     * @param claims Token claims
     * @return Authorization information
     */
    public AuthInfo generateAuthInfo(Claims claims) {
        String username = claims.getSubject();
        int id = 0;
        try {
            id = (int) claims.get("cartId");
        } catch (Exception ignored) {
        }
        List<LinkedHashMap<String, String>> rolesList = (List<LinkedHashMap<String, String>>) claims.get("roles");
        Set<Role> roles = new HashSet<>();

        for (LinkedHashMap<String, String> roleEntry : rolesList) {
            String roleName = roleEntry.get("authority");
            Role role = roleRepository.findByName(roleName);
            roles.add(role);
        }

        return new AuthInfo(username, id, roles);
    }
}
