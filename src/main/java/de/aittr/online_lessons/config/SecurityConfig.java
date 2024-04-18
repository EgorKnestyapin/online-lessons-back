package de.aittr.online_lessons.config;

import de.aittr.online_lessons.security.sec_filter.TokenFilter;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Class for security configuration.
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * {@link TokenFilter}
     */
    private TokenFilter filter;

    /**
     * Constructor for creating security config
     *
     * @param filter Filter for HTTP requests
     */
    public SecurityConfig(TokenFilter filter) {
        this.filter = filter;
    }

    /**
     * Getter
     *
     * @return Encoder
     */
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Setting up and protecting endpoints, security configuration
     *
     * @param http HTTP request
     * @return Security filter chain
     * @throws Exception Unexpected failure
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> {
                })
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        x -> x
                                .requestMatchers(HttpMethod.GET, "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/auth/login", "/api/auth/access")
                                .permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/auth/logout")
                                .hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/auth/auth_info").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/users/set_admin/{username}")
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/users/account_info/{username}")
                                .hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/users/change_password/{username}")
                                .hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/users/delete/{username}")
                                .hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/courses").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/courses/{id}").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/courses/{username}").hasRole("USER")
                                .requestMatchers(HttpMethod.PUT, "/api/courses/{id}")
                                .hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/courses/{id}")
                                .hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/courses/available/{username}")
                                .hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/courses/created/{username}")
                                .hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/lessons/{courseId}")
                                .hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/lessons", "/api/lessons/{courseId}")
                                .hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/lessons/{lessonsId}")
                                .hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/lessons/{lessonsId}")
                                .hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/cart/{cartId}").hasRole("USER")
                                .requestMatchers(HttpMethod.PUT, "/api/cart/add/{cartId}/{courseId}").hasRole("USER")
                                .requestMatchers(HttpMethod.DELETE, "/api/cart/{cartId}/{courseId}").hasRole("USER")
                                .requestMatchers(HttpMethod.DELETE, "/api/cart/clear/{cartId}").hasRole("USER")
                                .requestMatchers(HttpMethod.PUT, "/api/cart/buy/{cartId}").hasRole("USER")
                                .requestMatchers(HttpMethod.POST, "/api/files/**").permitAll()
//                                .requestMatchers(HttpMethod.POST, "/api/files/**").authenticated()
                                .anyRequest().permitAll())
                .addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Swagger setup
     *
     * @return API
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().
                        addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes
                        ("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info().title("Online lessons")
                        .description("Application for online lessons")
                        .version("1.0.0").contact(new Contact().name("Learn")
                                .email("learn@example.com").url("http://www.learn.com/"))
                        .license(new License().name("@Example")
                                .url("http://www.learn.com/")));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
