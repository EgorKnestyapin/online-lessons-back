package de.aittr.online_lessons.services;

import de.aittr.online_lessons.domain.dto.ChangePasswordDto;
import de.aittr.online_lessons.domain.dto.UserDto;
import de.aittr.online_lessons.domain.jpa.Cart;
import de.aittr.online_lessons.domain.jpa.Role;
import de.aittr.online_lessons.domain.jpa.User;
import de.aittr.online_lessons.exception_handling.exceptions.PasswordMismatchException;
import de.aittr.online_lessons.exception_handling.exceptions.UserNotFoundException;
import de.aittr.online_lessons.exception_handling.exceptions.UserValidationException;
import de.aittr.online_lessons.exception_handling.exceptions.UserAlreadyExistsException;
import de.aittr.online_lessons.repositories.jpa.CartRepository;
import de.aittr.online_lessons.repositories.jpa.UserRepository;
import de.aittr.online_lessons.mapping.UserMappingService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMappingService mappingService;
    private final CartRepository cartRepository;

    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository userRepository, UserMappingService mappingService, CartRepository cartRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.mappingService = mappingService;
        this.cartRepository = cartRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    @Transactional
    public UserDto register(UserDto userDto) {
        User user = mappingService.mapDtoToEntity(userDto);
        User foundUser = userRepository.findByUsername(user.getUsername());

        if (foundUser != null) {
            throw new UserAlreadyExistsException("User with the same nickname already exists");
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

    @Transactional
    public void setRoleAdmin(String username) {
        User user = (User) loadUserByUsername(username);
        user.addRole(new Role(2, "ROLE_ADMIN"));
    }

    @Transactional
    public User getUserById(int id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public UserDto getUserByUsername(String username) {
        User user = (User) loadUserByUsername(username);
        return mappingService.mapEntityToDto(user);
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User with this email was not found");
        }
        return user;
    }

    @Transactional
    public boolean changePassword(String username, ChangePasswordDto dto) {
        User user = (User) loadUserByUsername(username);
        String oldPassword = dto.getOldPassword();

        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            throw new PasswordMismatchException("New password and confirm password mismatch");
        }
        if (encoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(encoder.encode(dto.getNewPassword()));
            return true;
        }
        return false;
    }
}
