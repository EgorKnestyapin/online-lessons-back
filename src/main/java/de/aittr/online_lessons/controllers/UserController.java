package de.aittr.online_lessons.controllers;

import de.aittr.online_lessons.controllers.api.UserApi;
import de.aittr.online_lessons.domain.dto.ChangePasswordDto;
import de.aittr.online_lessons.domain.dto.UserDto;
import de.aittr.online_lessons.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController implements UserApi {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @Override
    public UserDto register(UserDto user) {
        return service.register(user);
    }

    @Override
    public void setRoleAdmin(String username) {
        service.setRoleAdmin(username);
    }

    @Override
    public UserDto getUserInfo(String username) {
        return service.getUserByUsername(username);
    }

    @Override
    public boolean changePassword(String username, ChangePasswordDto dto) {
        return service.changePassword(username, dto);
    }

    @Override
    public void deleteUser(String username) {
        service.deleteUserByUsername(username);
    }
}
