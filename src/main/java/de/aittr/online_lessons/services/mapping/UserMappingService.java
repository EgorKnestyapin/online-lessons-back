package de.aittr.online_lessons.services.mapping;

import de.aittr.online_lessons.domain.dto.UserDto;
import de.aittr.online_lessons.domain.jpa.Role;
import de.aittr.online_lessons.domain.jpa.User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserMappingService {
    public User mapDtoToEntity(UserDto userDto) {
        int id = userDto.getId();
        String username = userDto.getUsername();
        String email = userDto.getEmail();
        String password = userDto.getPassword();
        return new User(id, username, email, password, new HashSet<>());
    }

    public UserDto mapEntityToDto(User user) {
        int id = user.getId();
        String username = user.getUsername();
        String email = user.getEmail();
        String password = user.getPassword();
        Set<Role> roles = user.getRoles();
        return new UserDto(id, username, email, password, roles);
    }
}
