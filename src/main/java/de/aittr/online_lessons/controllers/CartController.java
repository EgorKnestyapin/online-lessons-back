package de.aittr.online_lessons.controllers;

import de.aittr.online_lessons.controllers.api.CartApi;
import de.aittr.online_lessons.domain.dto.CourseDto;
import de.aittr.online_lessons.services.jpa.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController implements CartApi {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public List<CourseDto> getCourses(int cartId) {
        return cartService.getCourses(cartId);
    }

    @Override
    public boolean addCourseToCart(int cartId, int courseId) {
        return cartService.addCourseToCart(cartId, courseId);
    }

    @Override
    public void deleteCourseFromCart(int cartId, int courseId) {
        cartService.deleteCourseFromCart(cartId, courseId);
    }

    @Override
    public void clearCart(int cartId) {
        cartService.clearCart(cartId);
    }

    @Override
    public void buyCourses(int cartId) {
        cartService.buyCourses(cartId);
    }
}
