package de.aittr.online_lessons.services;

import de.aittr.online_lessons.domain.dto.CourseDto;
import de.aittr.online_lessons.repositories.jpa.CartRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@TestPropertySource(locations = "classpath:application.properties")
class CartServiceTest {

    @Autowired
    private CartService cartService;

//    @Autowired
//    private CourseMappingService courseMappingService;
//
//    @Autowired
//    private EnrollmentRepository enrollmentRepository;
//
    @Autowired
    private CartRepository repository;



    @BeforeEach
    void setUp() {}

    @Test
    void getCourses() {
    }

    @Test
    void addCourseToCart() {
        cartService.addCourseToCart(2, 1);
        List<CourseDto> courses = cartService.getCourses(2);
        System.out.println(courses);
    }

    @Test
    void deleteCourseFromCart() {
    }

    @Test
    void clearCart() {
    }

    @Test
    void buyCourses() {
    }
}