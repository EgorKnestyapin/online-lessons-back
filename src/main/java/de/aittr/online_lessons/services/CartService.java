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
import de.aittr.online_lessons.repositories.jpa.RoleRepository;
import de.aittr.online_lessons.security.sec_dto.TokenResponseDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service containing tools for working with cart entity {@link Cart}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@Service
public class CartService {

    /**
     * {@link CartRepository}
     */
    private CartRepository cartRepository;

    /**
     * {@link CourseService}
     */
    private CourseService courseService;

    /**
     * {@link CourseMappingService}
     */
    private CourseMappingService courseMappingService;

    /**
     * {@link EnrollmentRepository}
     */
    private EnrollmentRepository enrollmentRepository;

    /**
     * Constructor for creating cart service
     *
     * @param cartRepository       Cart repository
     * @param courseService        Course service
     * @param courseMappingService Mapping service for course
     * @param enrollmentRepository Enrollment repository
     */
    public CartService(CartRepository cartRepository, CourseService courseService,
                       CourseMappingService courseMappingService, EnrollmentRepository enrollmentRepository) {
        this.cartRepository = cartRepository;
        this.courseService = courseService;
        this.courseMappingService = courseMappingService;
        this.enrollmentRepository = enrollmentRepository;
    }

    /**
     * Receiving courses in the cart
     *
     * @param cartId Cart ID
     * @return List of courses
     */
    public List<CourseDto> getCourses(int cartId) {
        Cart cart = getCartById(cartId);
        List<Course> courses = cart.getCourses();
        return courses.stream()
                .map(courseMappingService::mapEntityToDto)
                .toList();
    }

    /**
     * Adding a course to cart
     *
     * @param cartId   Cart ID
     * @param courseId Course ID
     * @throws CourseDuplicateException Course is already in the cart
     */
    @Transactional
    public void addCourseToCart(int cartId, int courseId) {
        Cart cart = getCartById(cartId);
        Course course = courseService.getCourseEntityById(courseId);
        if (cart.getCourses().contains(course)) {
            throw new CourseDuplicateException("Course is already in the cart");
        }
        cart.addCourse(course);
    }

    /**
     * Getting cart by ID from the database
     *
     * @param cartId Cart ID
     * @return Cart
     * @throws CartNotFoundException Cart not found
     */
    public Cart getCartById(int cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException("Cart with ID " + cartId + " not found");
        }
        return cart;
    }

    /**
     * Removing a course from the cart
     *
     * @param cartId   Cart ID
     * @param courseId Course ID
     */
    @Transactional
    public void deleteCourseFromCart(int cartId, int courseId) {
        Cart cart = getCartById(cartId);
        Course course = courseService.getCourseEntityById(courseId);
        cart.removeCourse(course);
    }

    /**
     * Removing all courses from the cart
     *
     * @param cartId Cart ID
     */
    @Transactional
    public void clearCart(int cartId) {
        Cart cart = getCartById(cartId);
        cart.getCourses().clear();
    }

    /**
     * Purchasing all courses in the cart
     *
     * @param cartId Cart ID
     * @throws EnrollmentAlreadyExistsException Enrollment with that course already exists
     * @throws EnrollmentValidationException    Incorrect values of enrollment fields
     */
    @Transactional
    public void buyCourses(int cartId) {
        Cart cart = getCartById(cartId);
        User user = cart.getUser();
        List<Course> courses = new ArrayList<>(cart.getCourses());

        courses.forEach(course -> {
            List<Enrollment> enrollments = enrollmentRepository.findByCourseId(course.getId());
            enrollments.forEach(enrollment -> {
                if (enrollment.getUser().equals(user)) {
                    throw new EnrollmentAlreadyExistsException("Enrollment with that course already exists");
                }
            });

            Enrollment enrollment = new Enrollment(0, LocalDateTime.now(), "active", user, course);
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
