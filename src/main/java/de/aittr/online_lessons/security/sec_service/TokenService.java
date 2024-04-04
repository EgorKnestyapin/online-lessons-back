package de.aittr.online_lessons.security.sec_service;

import de.aittr.online_lessons.domain.jpa.Role;
import de.aittr.online_lessons.domain.jpa.Token;
import de.aittr.online_lessons.domain.jpa.User;
import de.aittr.online_lessons.repositories.jpa.RoleRepository;
import de.aittr.online_lessons.repositories.jpa.TokenRepository;
import de.aittr.online_lessons.security.sec_dto.AuthInfo;
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

@Service
public class TokenService {

    private SecretKey accessKey;

    private SecretKey refreshKey;

    private RoleRepository roleRepository;

    private TokenRepository tokenRepository;

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

    public String generateAccessToken(@Nonnull User user) {
        LocalDateTime currentDate = LocalDateTime.now();
        Instant expirationInstant = currentDate.plusDays(1).atZone(ZoneId.systemDefault()).toInstant();
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


    public String generateRefreshToken(@Nonnull User user) {
        LocalDateTime currentDate = LocalDateTime.now();
        Instant expirationInstant = currentDate.plusDays(7).atZone(ZoneId.systemDefault()).toInstant();
        Date expirationDate = Date.from(expirationInstant);

        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(expirationDate)
                .signWith(refreshKey)
                .compact();
    }

    public boolean validateAccessToken(@Nonnull String accessToken) {
        return validateToken(accessToken, accessKey);
    }

    public boolean validateRefreshToken(@Nonnull String refreshToken) {
        return validateToken(refreshToken, refreshKey);
    }

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

    public Token saveRefreshToken(String refreshToken, User user) {
        int id = 0;
        if (user.getToken() != null) {
            id = user.getToken().getId();
        }
        Token newToken = new Token(id, refreshToken, user);
        return tokenRepository.save(newToken);
    }

    public Claims getAccessClaims(@Nonnull String accessToken) {
        return getClaims(accessToken, accessKey);
    }

    public Claims getRefreshClaims(@Nonnull String refreshToken) {
        return getClaims(refreshToken, refreshKey);
    }

    private Claims getClaims(@Nonnull String token, @Nonnull SecretKey key) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

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
