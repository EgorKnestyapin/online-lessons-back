package de.aittr.online_lessons.services.jpa;

import de.aittr.online_lessons.domain.dto.UserDto;
import de.aittr.online_lessons.domain.jpa.Cart;
import de.aittr.online_lessons.domain.jpa.Role;
import de.aittr.online_lessons.domain.jpa.User;
import de.aittr.online_lessons.exception_handling.exceptions.UserValidationException;
import de.aittr.online_lessons.exception_handling.exceptions.UserAlreadyExistsException;
import de.aittr.online_lessons.repositories.jpa.UserRepository;
import de.aittr.online_lessons.services.mapping.UserMappingService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final UserMappingService mappingService;
    private final CartService cartService;

    public UserService(UserRepository repository, UserMappingService mappingService, CartService cartService) {
        this.repository = repository;
        this.mappingService = mappingService;
        this.cartService = cartService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    @Transactional
    public UserDto register(UserDto userDto) {
        User user = mappingService.mapDtoToEntity(userDto);
        User foundUser = repository.findByUsername(user.getUsername());

        if (foundUser != null) {
            throw new UserAlreadyExistsException("User with the same nickname already exists");
        }
        user.clearRoles();
        Role role = new Role(1, "ROLE_USER");
        user.addRole(role);
        Cart cart = cartService.saveCart(user);
        user.setCart(cart);

        try {
            user = repository.save(user);
        } catch (Exception e) {
            throw new UserValidationException("Incorrect values of user fields.", e);
        }

        return mappingService.mapEntityToDto(user);
    }

    @Transactional
    public void setRoleAdmin(String username) {
        User user = (User) loadUserByUsername(username);
        user.addRole(new Role(2, "ROLE_ADMIN"));
    }
}
