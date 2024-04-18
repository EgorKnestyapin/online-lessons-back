package de.aittr.online_lessons.security.sec_filter;

import de.aittr.online_lessons.security.sec_dto.AuthInfo;
import de.aittr.online_lessons.security.sec_service.TokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

/**
 * Service containing tools for service for filtering HTTP requests
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@Component
public class TokenFilter extends GenericFilterBean {

    /**
     * {@link TokenService}
     */
    private TokenService service;

    /**
     * Constructor for creating token filter
     *
     * @param service Token service
     */
    public TokenFilter(TokenService service) {
        this.service = service;
    }

    /**
     * Processing HTTP requests
     *
     * @param request     Request
     * @param response    Response
     * @param filterChain Filter chain
     * @throws IOException      Connection failed
     * @throws ServletException Servlet can throw when it encounters difficulty
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = getTokenFromRequest((HttpServletRequest) request);

        if (token != null && service.validateAccessToken(token)) {
            Claims claims = service.getAccessClaims(token);
            AuthInfo authInfo = service.generateAuthInfo(claims);
            authInfo.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(authInfo);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Getting a refresh token from a cookie
     *
     * @param request Request
     * @return Refresh token
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("Access-Token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        // Bearer kdsjnfmsdhfhsdufyjfsdffds
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}
