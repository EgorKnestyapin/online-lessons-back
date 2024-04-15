package de.aittr.online_lessons.services;

import de.aittr.online_lessons.domain.dto.CourseDto;
import de.aittr.online_lessons.domain.jpa.Cart;
import de.aittr.online_lessons.domain.jpa.Course;
import de.aittr.online_lessons.domain.jpa.Enrollment;
import de.aittr.online_lessons.domain.jpa.User;
import de.aittr.online_lessons.exception_handling.exceptions.CartNotFoundException;
import de.aittr.online_lessons.exception_handling.exceptions.CourseDuplicateException;
import de.aittr.online_lessons.exception_handling.exceptions.EnrollmentAlreadyExistsException;
import de.aittr.online_lessons.exception_handling.exceptions.EnrollmentValidationException;
import de.aittr.online_lessons.mapping.CourseMappingService;
import de.aittr.online_lessons.repositories.jpa.CartRepository;
import de.aittr.online_lessons.repositories.jpa.EnrollmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private CartRepository cartRepository;
    private CourseService courseService;
    private CourseMappingService courseMappingService;
    private EnrollmentRepository enrollmentRepository;

    public CartService(CartRepository cartRepository, CourseService courseService,
                       CourseMappingService courseMappingService, EnrollmentRepository enrollmentRepository) {
        this.cartRepository = cartRepository;
        this.courseService = courseService;
        this.courseMappingService = courseMappingService;
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<CourseDto> getCourses(int cartId) {
        Cart cart = getCartById(cartId);
        List<Course> courses = cart.getCourses();
        return courses.stream()
                .map(courseMappingService::mapEntityToDto)
                .toList();
    }

    @Transactional
    public void addCourseToCart(int cartId, int courseId) {
        Cart cart = getCartById(cartId);
        Course course = courseService.getCourseEntityById(courseId);
        if (cart.getCourses().contains(course)) {
            throw new CourseDuplicateException("Course is already in the cart");
        }
        cart.addCourse(course);
    }

    private Cart getCartById(int cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException("Cart with ID " + cartId + " not found");
        }
        return cart;
    }

    @Transactional
    public void deleteCourseFromCart(int cartId, int courseId) {
        Cart cart = getCartById(cartId);
        Course course = courseService.getCourseEntityById(courseId);
        cart.removeCourse(course);
    }

    @Transactional
    public void clearCart(int cartId) {
        Cart cart = getCartById(cartId);
        cart.getCourses().clear();
    }

    @Transactional
    public void buyCourses(int cartId) {
        Cart cart = getCartById(cartId);
        User user = cart.getUser();
        List<Course> courses = new ArrayList<>(cart.getCourses());

        courses.forEach(course -> {
            Enrollment enrollment = enrollmentRepository.findByCourseId(course.getId());

            if (enrollment != null && enrollment.getUser().equals(user)) {
                throw new EnrollmentAlreadyExistsException("Enrollment with that course already exists");
            }

            enrollment = new Enrollment(0, LocalDateTime.now(), "active", user, course);
            try {
                enrollmentRepository.save(enrollment);
                course.setCounter(course.getCounter() + 1);
            } catch (Exception e) {
                throw new EnrollmentValidationException("Incorrect values of enrollment fields.", e);
            }
        });

        cart.getCourses().clear();
    }
}
