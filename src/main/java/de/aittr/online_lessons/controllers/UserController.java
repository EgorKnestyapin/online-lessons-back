package de.aittr.online_lessons.controllers;

import de.aittr.online_lessons.domain.dto.UserDto;
import de.aittr.online_lessons.services.jpa.UserService;
import io.swagger.v3.oas.annotations.Operation;
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
    public UserDto register(@RequestBody UserDto user) {
        return service.register(user);
    }

    @PutMapping("/set_admin/{username}")
    @Operation(
            summary = "Добавление пользователю роли админа"
    )
    public void setRoleAdmin(@PathVariable String username) {
        service.setRoleAdmin(username);
    }
}
