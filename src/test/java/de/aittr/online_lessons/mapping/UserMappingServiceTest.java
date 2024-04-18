package de.aittr.online_lessons.mapping;

import de.aittr.online_lessons.domain.dto.UserDto;
import de.aittr.online_lessons.domain.jpa.User;
import jakarta.transaction.Transactional;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@TestPropertySource(locations = "classpath:test.properties")
class UserMappingServiceTest {

    @Autowired
    private UserMappingService userMappingService;

    @Test
    void mapDtoToEntityNull() {
        UserDto userDto = null;
        User user = userMappingService.mapDtoToEntity(userDto);

        Assert.assertEquals(null, user);
    }

    @Test
    void mapEntityToDto() {
        User user = null;
        UserDto userDto = userMappingService.mapEntityToDto(user);

        Assert.assertEquals(null, userDto);
    }
}