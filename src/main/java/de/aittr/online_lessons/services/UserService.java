package de.aittr.online_lessons.services;

import de.aittr.online_lessons.domain.dto.ChangePasswordDto;
import de.aittr.online_lessons.domain.dto.UserDto;
import de.aittr.online_lessons.domain.jpa.Cart;
import de.aittr.online_lessons.domain.jpa.Lesson;
import de.aittr.online_lessons.domain.jpa.Role;
import de.aittr.online_lessons.domain.jpa.User;
import de.aittr.online_lessons.exception_handling.exceptions.PasswordMismatchException;
import de.aittr.online_lessons.exception_handling.exceptions.UserNotFoundException;
import de.aittr.online_lessons.exception_handling.exceptions.UserValidationException;
import de.aittr.online_lessons.exception_handling.exceptions.UserAlreadyExistsException;
import de.aittr.online_lessons.repositories.jpa.CartRepository;
import de.aittr.online_lessons.repositories.jpa.LessonRepository;
import de.aittr.online_lessons.repositories.jpa.UserRepository;
import de.aittr.online_lessons.mapping.UserMappingService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Service containing tools for working with user entity {@link User}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@Service
public class UserService implements UserDetailsService {

    /**
     * {@link UserRepository}
     */
    private final UserRepository userRepository;

    /**
     * {@link UserMappingService}
     */
    private final UserMappingService mappingService;

    /**
     * {@link CartRepository}
     */
    private final CartRepository cartRepository;

    /**
     * Encoder
     */
    private final BCryptPasswordEncoder encoder;

    /**
     * Constructor for creating user service
     *
     * @param userRepository User repository
     * @param mappingService Mapping service for user
     * @param cartRepository Cart repository
     * @param encoder        Encoder
     */
    public UserService(UserRepository userRepository, UserMappingService mappingService, CartRepository cartRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.mappingService = mappingService;
        this.cartRepository = cartRepository;
        this.encoder = encoder;
    }

    /**
     * Getting user by nickname
     *
     * @param username User nickname
     * @return User
     * @throws UsernameNotFoundException User with this username not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User with this username not found");
        }

        return user;
    }

    /**
     * New user registration
     *
     * @param userDto User DTO
     * @return Saved user
     * @throws UserAlreadyExistsException User with this email or this nickname already exists
     * @throws UserValidationException    Incorrect values of user fields
     */
    @Transactional
    public UserDto register(UserDto userDto) {
        User user = mappingService.mapDtoToEntity(userDto);
        User foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser != null) {
            throw new UserAlreadyExistsException("This nickname is already taken");
        }

        foundUser = userRepository.findByEmail(user.getEmail());
        if (foundUser != null) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }

        user.clearRoles();
        Role role = new Role(1, "ROLE_USER");
        user.addRole(role);

        try {
            user = userRepository.save(user);
        } catch (Exception e) {
            throw new UserValidationException("Incorrect values of user fields.", e);
        }
        Cart cart = new Cart(0, new ArrayList<>(), user);
        cartRepository.save(cart);

        return mappingService.mapEntityToDto(user);
    }

    /**
     * Adding an admin role to a user
     *
     * @param username User nickname
     * @throws UserNotFoundException User with this nickname was not found
     */
    @Transactional
    public void setRoleAdmin(String username) {
        User user = (User) loadUserByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User with this nickname was not found");
        }
        user.addRole(new Role(2, "ROLE_ADMIN"));
    }

    /**
     * Getting user from database by ID
     *
     * @param id User ID
     * @return User
     * @throws UsernameNotFoundException User not found
     */
    @Transactional
    public User getUserById(int id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User with id " + id + " not found");
        }
        return user;
    }

    /**
     * Getting user from database by nickname
     *
     * @param username User nickname
     * @return User
     */
    public UserDto getUserByUsername(String username) {
        User user = (User) loadUserByUsername(username);
        return mappingService.mapEntityToDto(user);
    }

    /**
     * Getting user from database by email
     *
     * @param email User email
     * @return User
     * @throws UserNotFoundException User with this email already exists
     */
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User with this email already exists");
        }
        return user;
    }

    /**
     * Change user password
     *
     * @param username User nickname
     * @param dto      DTO to change password
     * @throws PasswordMismatchException Password mismatch
     */
    @Transactional
    public void changePassword(String username, ChangePasswordDto dto) {
        User user = (User) loadUserByUsername(username);
        String oldPassword = dto.getOldPassword();

        if (!encoder.matches(oldPassword, user.getPassword())) {
            throw new PasswordMismatchException("Current password is incorrect");
        }

        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            throw new PasswordMismatchException("New password and confirm password mismatch");
        }

        user.setPassword(encoder.encode(dto.getNewPassword()));
    }

    /**
     * Deleting a user from the database by nickname
     *
     * @param username User nickname
     */
    public void deleteUserByUsername(String username) {
        User user = (User) loadUserByUsername(username);
        userRepository.delete(user);
    }
}
