package de.aittr.online_lessons.services;

import de.aittr.online_lessons.domain.dto.CourseDto;
import de.aittr.online_lessons.domain.jpa.Cart;
import de.aittr.online_lessons.domain.jpa.Course;
import de.aittr.online_lessons.mapping.CourseMappingService;
import de.aittr.online_lessons.repositories.jpa.CartRepository;
import de.aittr.online_lessons.repositories.jpa.EnrollmentRepository;
import jakarta.transaction.Transactional;
import org.junit.Assert;
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

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CourseMappingService courseMappingService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getCourses() {
        List<CourseDto> courses = cartService.getCourses(1);
        Cart foundCart = cartService.getCartById(1);
        List<Course> expected = courseMappingService.mapListDtoToListEntity(courses);
        List<Course> actual = foundCart.getCourses();

        Assert.assertEquals(expected, actual);
    }

    @Test
    void addCourseToCart() {
        Course foundCourse = courseService.getCourseEntityById(3);
        Cart foundCart = cartService.getCartById(1);
        List<Course> actual = foundCart.getCourses();

        Assert.assertFalse(actual.contains(foundCourse));

        cartService.addCourseToCart(1, 3);
        List<CourseDto> courses = cartService.getCourses(1);
        List<Course> expected = courseMappingService.mapListDtoToListEntity(courses);
        actual = foundCart.getCourses();

        Assert.assertEquals(expected, actual);
        Assert.assertTrue(actual.contains(foundCourse));
    }

    @Test
    void getCartById() {
        Cart expected = cartService.getCartById(1);
        Cart actual = cartRepository.findById(1).orElse(null);

        Assert.assertEquals(expected, actual);
    }

    @Test
    void deleteCourseFromCart() {
        Course foundCourse = courseService.getCourseEntityById(3);
        cartService.addCourseToCart(1, 3);
        Cart foundCart = cartService.getCartById(1);
        List<CourseDto> courses = cartService.getCourses(1);
        List<Course> expected = courseMappingService.mapListDtoToListEntity(courses);
        List<Course> actual = foundCart.getCourses();

        Assert.assertEquals(expected, actual);
        Assert.assertTrue(actual.contains(foundCourse));

        cartService.deleteCourseFromCart(1, 3);
        actual = foundCart.getCourses();

        Assert.assertFalse(actual.contains(foundCourse));
    }

    @Test
    void clearCart() {
        Course course1 = courseService.getCourseEntityById(1);
        cartService.addCourseToCart(1, 1);
        Course course2 = courseService.getCourseEntityById(2);
        cartService.addCourseToCart(1, 2);
        Course course3 = courseService.getCourseEntityById(3);
        cartService.addCourseToCart(1, 3);
        List<Course> courses = List.of(course1, course2, course3);
        Cart foundCart = cartService.getCartById(1);
        List<Course> actualBeforeClear = foundCart.getCourses();

        courses.forEach(course -> {
            Assert.assertTrue(actualBeforeClear.contains(course));
        });

        cartService.clearCart(1);
        List<Course> actualAfterClear = foundCart.getCourses();

        courses.forEach(course -> {
            Assert.assertFalse(actualAfterClear.contains(course));
        });
    }

    @Test
    void buyCourses() {
        Cart foundCart = cartService.getCartById(1);
        cartService.addCourseToCart(1, 3);
        cartService.addCourseToCart(1, 4);
        cartService.addCourseToCart(1, 5);
        int enrollmentSizeBefore = enrollmentRepository.findAll().size();
        int courseSizeBefore = foundCart.getCourses().size();
        cartService.buyCourses(1);
        int enrollmentSizeAfter = enrollmentRepository.findAll().size();
        int courseSizeAfter = foundCart.getCourses().size();

        Assert.assertEquals(enrollmentSizeBefore + 3, enrollmentSizeAfter);
        Assert.assertEquals(courseSizeBefore - 3, courseSizeAfter);
    }
}