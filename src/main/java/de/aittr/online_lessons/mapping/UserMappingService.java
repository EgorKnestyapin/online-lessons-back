package de.aittr.online_lessons.mapping;

import de.aittr.online_lessons.domain.dto.UserDto;
import de.aittr.online_lessons.domain.jpa.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Service containing tools for user mapping {@link User}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@Mapper(componentModel = "spring")
public interface UserMappingService {

    @Autowired
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Mapping(target = "id", constant = "0")
    @Mapping(source = "dto.nickname", target = "username")
    @Mapping(target = "password", expression = "java(encoder.encode(dto.getPassword()))")
    User mapDtoToEntity(UserDto dto);

    @Mapping(source = "user.username", target = "nickname")
    @Mapping(target = "password", constant = "password is hidden")
    UserDto mapEntityToDto(User user);
}
