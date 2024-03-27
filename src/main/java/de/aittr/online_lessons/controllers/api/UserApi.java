package de.aittr.online_lessons.controllers.api;

import de.aittr.online_lessons.domain.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tags(
        @Tag(name = "Users")
)
public interface UserApi {
    @PostMapping("/register")
    @Operation(
            summary = "Регистрация нового пользователя",
            description = "Сохранение в базу данных нового пользователя, переданного в теле запроса"
    )
    UserDto register(
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Объект ДТО пользователя")
            @RequestBody
            UserDto user
    );

    @PutMapping("/set_admin/{username}")
    @Operation(
            summary = "Добавление пользователю роли админа"
    )
    void setRoleAdmin(
            @PathVariable
            @Parameter(description = "Никнейм пользователя")
            String username

    );
}
