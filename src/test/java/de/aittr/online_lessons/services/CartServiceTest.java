package de.aittr.online_lessons.services;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class CartServiceTest {

    @Autowired
//    @Qualifier("cartService")
    private CartService cartService;

//    @Autowired
//    private CourseMappingService courseMappingService;
//
//    @Autowired
//    private EnrollmentRepository enrollmentRepository;
//
//    @Autowired
//    private CartRepository repository;



    @BeforeEach
    void setUp() {}

    @Test
    void getCourses() {
    }

    @Test
    void addCourseToCart() {
//        cartService.addCourseToCart(3, 1);
//   List<CourseDto> courses = cartService.getCourses(3);
        System.out.println("hello");
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