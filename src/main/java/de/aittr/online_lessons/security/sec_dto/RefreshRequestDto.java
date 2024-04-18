package de.aittr.online_lessons.security.sec_dto;

import de.aittr.online_lessons.security.sec_service.TokenService;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

/**
 * Class describing the refresh token request DTO.
 * TokenService is provided for working with refresh token objects {@link TokenService}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@Schema(
        description = "Dto that received from client for purpose to receive new access token" +
                " and contains refresh token"
)
public class RefreshRequestDto {

    /**
     * Refresh token
     */
    @Schema(description = "Refresh token")
    String refreshToken;

    /**
     * Getter
     *
     * @return Refresh token
     * @see RefreshRequestDto#refreshToken
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefreshRequestDto that = (RefreshRequestDto) o;
        return Objects.equals(refreshToken, that.refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(refreshToken);
    }

    /**
     * Method to convert a refresh token object to a string representation
     *
     * @return Token refresh object as a string
     */
    @Override
    public String toString() {
        return "RefreshRequestDto{" +
                "refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
