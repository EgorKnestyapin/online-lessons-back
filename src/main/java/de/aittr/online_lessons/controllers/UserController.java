package de.aittr.online_lessons.controllers;

import de.aittr.online_lessons.controllers.api.UserApi;
import de.aittr.online_lessons.domain.dto.ChangePasswordDto;
import de.aittr.online_lessons.domain.dto.UserDto;
import de.aittr.online_lessons.services.LessonService;
import de.aittr.online_lessons.services.UserService;
import org.springframework.web.bind.annotation.*;

/**
 * Controller that accepts requests related to user {@link UserService}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@RestController
public class UserController implements UserApi {
    /**
     * {@link UserService}
     */
    private final UserService service;

    /**
     * Constructor for creating user controller
     *
     * @param service User service
     */
    public UserController(UserService service) {
        this.service = service;
    }

    /**
     * New user registration
     *
     * @param user User DTO
     * @return Created user
     */
    @Override
    public UserDto register(UserDto user) {
        return service.register(user);
    }

    /**
     * Adding admin role to a user
     *
     * @param username User nickname
     */
    @Override
    public void setRoleAdmin(String username) {
        service.setRoleAdmin(username);
    }

    /**
     * Getting user information
     *
     * @param username User nickname
     * @return User
     */
    @Override
    public UserDto getUserInfo(String username) {
        return service.getUserByUsername(username);
    }

    /**
     * Change user password
     *
     * @param username User nickname
     * @param dto
     */
    @Override
    public void changePassword(String username, ChangePasswordDto dto) {
        service.changePassword(username, dto);
    }

    /**
     * Deleting user
     *
     * @param username User nickname
     */
    @Override
    public void deleteUser(String username) {
        service.deleteUserByUsername(username);
    }
}
