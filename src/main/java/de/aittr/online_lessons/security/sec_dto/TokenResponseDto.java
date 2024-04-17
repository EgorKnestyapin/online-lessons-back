package de.aittr.online_lessons.security.sec_dto;

import de.aittr.online_lessons.security.sec_service.TokenService;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

/**
 * Class describing the token response DTO.
 * TokenService is provided for working with token objects {@link TokenService}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@Schema(
        description = "Dto that transfer to user after successfully authentication and contains access" +
                " and refresh tokens"
)
public class TokenResponseDto {

    /**
     * Access token
     */
    @Schema(description = "Access token")
    String accessToken;

    /**
     * Refresh token
     */
    @Schema(description = "Refresh token")
    String refreshToken;

    /**
     * Error message
     */
    @Schema(description = "Message about error reasons (if some error occurs)")
    String message;

    /**
     * Constructor for creating token information
     *
     * @param accessToken  Access token
     * @param refreshToken Refresh token
     * @see TokenResponseDto#TokenResponseDto(String)
     */
    public TokenResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    /**
     * Constructor for creating error message
     *
     * @param message Error message
     * @see TokenResponseDto#TokenResponseDto(String, String)
     */
    public TokenResponseDto(String message) {
        this.message = message;
    }

    /**
     * Getter
     *
     * @return Access token
     * @see TokenResponseDto#accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Getter
     *
     * @return Refresh token
     * @see TokenResponseDto#refreshToken
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Getter
     *
     * @return Error message
     * @see TokenResponseDto#message
     */
    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenResponseDto that = (TokenResponseDto) o;
        return Objects.equals(accessToken, that.accessToken) && Objects.equals(refreshToken, that.refreshToken) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessToken, refreshToken, message);
    }

    /**
     * Method to convert token objects to a string representation
     *
     * @return Token objects as a string
     */
    @Override
    public String toString() {
        return "TokenResponseDto{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
