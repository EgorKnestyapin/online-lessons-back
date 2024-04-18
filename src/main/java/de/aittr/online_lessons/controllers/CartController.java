package de.aittr.online_lessons.controllers;

import de.aittr.online_lessons.controllers.api.CartApi;
import de.aittr.online_lessons.domain.dto.CourseDto;
import de.aittr.online_lessons.security.sec_service.AuthService;
import de.aittr.online_lessons.services.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller that accepts requests related to cart {@link CartService}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@RestController
public class CartController implements CartApi {

    /**
     * {@link CartService}
     */
    private final CartService cartService;

    /**
     * Constructor for creating cart controller
     *
     * @param cartService Cart service
     */
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * Getting all courses in the cart
     *
     * @param cartId Cart ID
     * @return List of courses
     */
    @Override
    public List<CourseDto> getCourses(int cartId) {
        return cartService.getCourses(cartId);
    }

    /**
     * Adding a course to the cart
     *
     * @param cartId Cart ID
     * @param courseId Course ID
     */
    @Override
    public void addCourseToCart(int cartId, int courseId) {
       cartService.addCourseToCart(cartId, courseId);
    }

    /**
     * Deleting a course from the cart
     *
     * @param cartId Cart ID
     * @param courseId Course ID
     */
    @Override
    public void deleteCourseFromCart(int cartId, int courseId) {
        cartService.deleteCourseFromCart(cartId, courseId);
    }

    /**
     * Removing all courses from the cart
     *
     * @param cartId Cart ID
     */
    @Override
    public void clearCart(int cartId) {
        cartService.clearCart(cartId);
    }

    /**
     * Purchasing all courses in the cart
     *
     * @param cartId Cart ID
     */
    @Override
    public void buyCourses(int cartId) {
        cartService.buyCourses(cartId);
    }
}
