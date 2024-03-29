package de.aittr.online_lessons.services.jpa;

import de.aittr.online_lessons.domain.dto.UserDto;
import de.aittr.online_lessons.domain.jpa.Cart;
import de.aittr.online_lessons.domain.jpa.Role;
import de.aittr.online_lessons.domain.jpa.User;
import de.aittr.online_lessons.exception_handling.exceptions.UserValidationException;
import de.aittr.online_lessons.exception_handling.exceptions.UserAlreadyExistsException;
import de.aittr.online_lessons.repositories.jpa.CartRepository;
import de.aittr.online_lessons.repositories.jpa.UserRepository;
import de.aittr.online_lessons.services.mapping.UserMappingService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMappingService mappingService;
    private final CartRepository cartRepository;

    public UserService(UserRepository userRepository, UserMappingService mappingService, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.mappingService = mappingService;
        this.cartRepository = cartRepository;
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
}
