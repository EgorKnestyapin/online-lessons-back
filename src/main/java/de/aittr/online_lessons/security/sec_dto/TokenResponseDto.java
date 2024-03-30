package de.aittr.online_lessons.security.sec_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Objects;

@Schema(
        description = "Dto that transfer to user after successfully authentication and contains access" +
                " and refresh tokens"
)
public class TokenResponseDto {

    @Schema(description = "Access token")
    String accessToken;

    @Schema(description = "Refresh token")
    String refreshToken;

    @Schema(description = "Message about error reasons (if some error occurs)")
    String message;

    public TokenResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public TokenResponseDto(String message) {
        this.message = message;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

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

    @Override
    public String toString() {
        return "TokenResponseDto{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
