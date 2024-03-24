package de.aittr.online_lessons.controllers;

import de.aittr.online_lessons.domain.dto.UserDto;
import de.aittr.online_lessons.services.jpa.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    @Operation(
            summary = "Регистрация нового пользователя",
            description = "Сохранение в базу данных нового пользователя, переданного в теле запроса"
    )
    public UserDto register(
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Объект ДТО пользователя")
            @RequestBody
            UserDto user
    ) {
        return service.register(user);
    }

    @PutMapping("/set_admin/{username}")
    @Operation(
            summary = "Добавление пользователю роли админа"
    )
    public void setRoleAdmin(
            @PathVariable
            @Parameter(description = "Никнейм пользователя")
            String username

    ) {
        service.setRoleAdmin(username);
    }
}
