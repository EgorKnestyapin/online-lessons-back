package de.aittr.online_lessons.services.jpa;

import de.aittr.online_lessons.domain.dto.UserDto;
import de.aittr.online_lessons.domain.jpa.Role;
import de.aittr.online_lessons.domain.jpa.User;
import de.aittr.online_lessons.exceptions.UserAlreadyExistsException;
import de.aittr.online_lessons.repositories.jpa.UserRepository;
import de.aittr.online_lessons.services.mapping.UserMappingService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder;
    private final UserMappingService mappingService;

    public UserService(UserRepository repository, BCryptPasswordEncoder encoder, UserMappingService mappingService) {
        this.repository = repository;
        this.encoder = encoder;
        this.mappingService = mappingService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public UserDto register(UserDto userDto) {
        User user = mappingService.mapDtoToEntity(userDto);
        User foundUser = repository.findByUsername(user.getUsername());

        if (foundUser != null) {
            throw new UserAlreadyExistsException("Пользователь с таким именем уже существует");
        }
        user.clearRoles();
        Role role = new Role(1, "ROLE_USER");
        user.addRole(role);

        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user = repository.save(user);

        return mappingService.mapEntityToDto(user);
    }

    @Transactional
    public void setRoleAdmin(String username) {
        User user = (User) loadUserByUsername(username);
        user.addRole(new Role(2, "ROLE_ADMIN"));
    }
}
