package de.aittr.online_lessons.mapping;

import de.aittr.online_lessons.domain.dto.UserDto;
import de.aittr.online_lessons.domain.jpa.Role;
import de.aittr.online_lessons.domain.jpa.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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
