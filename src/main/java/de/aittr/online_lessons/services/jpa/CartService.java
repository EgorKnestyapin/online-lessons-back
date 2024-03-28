package de.aittr.online_lessons.services.jpa;

import de.aittr.online_lessons.domain.dto.CourseDto;
import de.aittr.online_lessons.domain.jpa.Cart;
import de.aittr.online_lessons.domain.jpa.Course;
import de.aittr.online_lessons.domain.jpa.Enrollment;
import de.aittr.online_lessons.domain.jpa.User;
import de.aittr.online_lessons.exception_handling.exceptions.CartNotFoundException;
import de.aittr.online_lessons.repositories.jpa.CartRepository;
import de.aittr.online_lessons.repositories.jpa.EnrollmentRepository;
import de.aittr.online_lessons.repositories.jpa.UserRepository;
import de.aittr.online_lessons.services.mapping.CourseMappingService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CourseService courseService;
    private final CourseMappingService courseMappingService;
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, CourseService courseService, CourseMappingService courseMappingService, EnrollmentRepository enrollmentRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.courseService = courseService;
        this.courseMappingService = courseMappingService;
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
    }

    public List<CourseDto> getCourses(int cartId) {
        Cart cart = getCartById(cartId);
        List<Course> courseList = cart.getCourseList();
        return courseList.stream()
                .map(courseMappingService::mapEntityToDto)
                .toList();
    }

    @Transactional
    public void addCourseToCart(int cartId, int courseId) {
        Cart cart = getCartById(cartId);
        Course course = courseService.getCourseEntityById(courseId);
        cart.addCourse(course);
    }

    private Cart getCartById(int cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException("Cart not found");
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
        cart.getCourseList().clear();
    }

    @Transactional
    public void buyCourses(int cartId) {
        Enrollment enrollment = new Enrollment(0, LocalDateTime.now(), "active");
        Cart cart = getCartById(cartId);
        System.out.println(cart.getUser());
        List<Course> courses = cart.getCourseList();
        User user = cart.getUser();
        user.getAvailableCourses().addAll(courses);
//        userRepository.save(user);
        enrollmentRepository.save(enrollment);
    }
}
