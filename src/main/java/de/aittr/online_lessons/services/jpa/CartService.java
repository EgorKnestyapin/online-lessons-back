package de.aittr.online_lessons.services.jpa;

import de.aittr.online_lessons.domain.dto.CourseDto;
import de.aittr.online_lessons.domain.jpa.Cart;
import de.aittr.online_lessons.domain.jpa.Course;
import de.aittr.online_lessons.domain.jpa.User;
import de.aittr.online_lessons.exception_handling.exceptions.CartNotFoundException;
import de.aittr.online_lessons.exception_handling.exceptions.CourseNotFoundException;
import de.aittr.online_lessons.repositories.jpa.CartRepository;
import de.aittr.online_lessons.services.mapping.CourseMappingService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private final CartRepository repository;
    private final CourseService courseService;
    private final CourseMappingService courseMappingService;

    public CartService(CartRepository repository, CourseService courseService, CourseMappingService courseMappingService) {
        this.repository = repository;
        this.courseService = courseService;
        this.courseMappingService = courseMappingService;
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
        Course course = getCourseById(courseId);
        cart.addCourse(course);
    }

    private Course getCourseById(int courseId) {
        Course course = courseService.getCourseEntityById(courseId);
        if (course == null) {
            throw new CourseNotFoundException("Course not found");
        }
        return course;
    }

    private Cart getCartById(int cartId) {
        Cart cart = repository.findById(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException("Cart not found");
        }
        return cart;
    }

    @Transactional
    public void deleteCourseFromCart(int cartId, int courseId) {
        Cart cart = getCartById(cartId);
        Course course = getCourseById(courseId);
        cart.removeCourse(course);
    }

    @Transactional
    public void clearCart(int cartId) {
        Cart cart = getCartById(cartId);
        cart.getCourseList().clear();
    }

    public Cart saveCart(User user) {
        Cart cart = new Cart(0, new ArrayList<>(), user);
        return repository.save(cart);
    }
}
