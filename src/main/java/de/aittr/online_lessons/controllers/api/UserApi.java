package de.aittr.online_lessons.controllers.api;

import de.aittr.online_lessons.domain.dto.ChangePasswordDto;
import de.aittr.online_lessons.domain.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@Tags(
        @Tag(name = "User controller", description = "Controller for some operations with users")
)
@RequestMapping("/api/users")
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

    @GetMapping("/account_info/{username}")
    @Operation(
            summary = "Получение информации об аккаунте пользователя"
    )
    UserDto getUserInfo(
            @PathVariable
            @Parameter(description = "Никнейм пользователя")
            String username
    );

    @PutMapping("/change_password/{username}")
    @Operation(
            summary = "Смена пароля пользователя"
    )
    boolean changePassword(
            @PathVariable
            @Parameter(description = "Никнейм пользователя")
            String username,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Объект ДТО для смены пароля")
            @RequestBody
            ChangePasswordDto dto
    );
}
