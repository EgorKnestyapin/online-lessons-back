package de.aittr.online_lessons.controllers;

import de.aittr.online_lessons.domain.dto.UserDto;
import de.aittr.online_lessons.services.jpa.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public UserDto register(@RequestBody UserDto user) {
        return service.register(user);
    }

    @PutMapping("/set_admin/{username}")
    public void setRoleAdmin(@PathVariable String username) {
        service.setRoleAdmin(username);
    }
}
