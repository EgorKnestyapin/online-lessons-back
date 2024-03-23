package de.aittr.online_lessons.services.mapping;

import de.aittr.online_lessons.domain.dto.UserDto;
import de.aittr.online_lessons.domain.jpa.Role;
import de.aittr.online_lessons.domain.jpa.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMappingService {

    @Mapping(target = "id", constant = "0")
    @Mapping(source = "dto.nickname", target = "username")
    @Mapping(target = "password", expression = "java(String.valueOf(dto.getPassword().hashCode()))")
    User mapDtoToEntity(UserDto dto);

    @Mapping(source = "user.username", target = "nickname")
    @Mapping(target = "password", constant = "password is hidden")
    UserDto mapEntityToDto(User user);
}
