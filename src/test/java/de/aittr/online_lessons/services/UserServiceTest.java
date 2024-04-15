package de.aittr.online_lessons.services;

import de.aittr.online_lessons.domain.dto.ChangePasswordDto;
import de.aittr.online_lessons.domain.dto.UserDto;
import de.aittr.online_lessons.domain.jpa.Role;
import de.aittr.online_lessons.domain.jpa.User;
import de.aittr.online_lessons.mapping.UserMappingService;
import de.aittr.online_lessons.repositories.jpa.CartRepository;
import de.aittr.online_lessons.repositories.jpa.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@TestPropertySource(locations = "classpath:application.properties")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMappingService userMappingService;

    @Autowired
    private CartRepository cartRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void loadUserByUsername() {
        User expected = (User) userService.loadUserByUsername("user123");
        User actual = userRepository.findByUsername("user123");

        Assert.assertEquals(expected, actual);
    }

    @Test
    void register() {
        int userSizeBefore = userRepository.findAll().size();
        int cartSizeBefore = cartRepository.findAll().size();
        UserDto userDto = new UserDto(0, "michael", "michael322@gmail.com",
                "qwerty123", null);
        User expected = userMappingService.mapDtoToEntity(userDto);
        UserDto registeredUser = userService.register(userDto);
        User actual = userMappingService.mapDtoToEntity(registeredUser);
        int cartSizeAfter = cartRepository.findAll().size();
        int userSizeAfter = userRepository.findAll().size();

        Assert.assertEquals(expected.getUsername(), actual.getUsername());
        Assert.assertEquals(expected.getEmail(), actual.getEmail());
        Assert.assertEquals(userSizeBefore + 1, userSizeAfter);
        Assert.assertEquals(cartSizeBefore + 1, cartSizeAfter);
    }

    @Test
    void setRoleAdmin() {
        User user = (User) userService.loadUserByUsername("user123");
        Role role = new Role(2, "ROLE_ADMIN");

        Assert.assertFalse(user.getRoles().contains(role));

        userService.setRoleAdmin("user123");
        user = (User) userService.loadUserByUsername("user123");

        Assert.assertTrue(user.getRoles().contains(role));
    }

    @Test
    void getUserById() {
        User expected = userService.getUserById(1);
        User actual = userRepository.findById(1).orElse(null);

        Assert.assertEquals(expected, actual);
    }

    @Test
    void getUserByUsername() {
        UserDto user = userService.getUserByUsername("user123");
        User expected = userMappingService.mapDtoToEntity(user);
        User actual = userRepository.findByUsername("user123");

        Assert.assertEquals(expected.getUsername(), actual.getUsername());
        Assert.assertEquals(expected.getEmail(), actual.getEmail());
        Assert.assertEquals(expected.getRoles(), actual.getRoles());
    }

    @Test
    void getUserByEmail() {
        User expected = userService.getUserByEmail("user123@example.com");
        User actual = userRepository.findByEmail("user123@example.com");

        Assert.assertEquals(expected, actual);
    }

    @Test
    void changePassword() {
        String oldUserPassword = userRepository.findByUsername("user123").getPassword();
        ChangePasswordDto changePasswordDto = new ChangePasswordDto("Testtest1!",
                "Testtest2!", "Testtest2!");
        userService.changePassword("user123", changePasswordDto);
        String newUserPassword = userRepository.findByUsername("user123").getPassword();

        Assert.assertNotEquals(oldUserPassword, newUserPassword);
    }

    @Test
    void deleteUserByUsername() {
        int userSizeBefore = userRepository.findAll().size();
        User foundUser = userRepository.findByUsername("user123");

        Assert.assertNotNull(foundUser);

        userService.deleteUserByUsername("user123");
        int userSizeAfter = userRepository.findAll().size();
        foundUser = userRepository.findByUsername("user123");

        Assert.assertNull(foundUser);
        Assert.assertEquals(userSizeBefore - 1, userSizeAfter);
    }
}