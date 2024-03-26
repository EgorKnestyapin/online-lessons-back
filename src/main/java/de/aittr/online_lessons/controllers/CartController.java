package de.aittr.online_lessons.controllers;

import de.aittr.online_lessons.domain.dto.CourseDto;
import de.aittr.online_lessons.services.jpa.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{cartId}")
    @Operation(
            summary = "Получение курсов в корзине",
            description = "Получение курсов из базы данных корзины, идентификатор которой передан в строке запроса"
    )
    public List<CourseDto> getCourses(
            @PathVariable
            @Parameter(description = "Идентификатор корзины")
            int cartId
    ) {
        return cartService.getCourses(cartId);
    }

    @PutMapping("/{cartId}/{courseId}")
    @Operation(
            summary = "Добавление курса в корзину",
            description = "Сохранение курса в базу данных корзины по идентификатору, переданному в строке запроса"
    )
    public void addProductToCart(
            @PathVariable
            @Parameter(description = "Идентификатор корзины")
            int cartId,
            @PathVariable
            @Parameter(description = "Идентификатор курса")
            int courseId
    ) {
        cartService.addCourseToCart(cartId, courseId);
    }

    @DeleteMapping("/{cartId}/{courseId}")
    @Operation(
            summary = "Удаление курса из корзины",
            description = "Удаление курса из базы данных корзины по идентификатору, переданному в строке запроса"
    )
    public void deleteProductFromCart(
            @PathVariable
            @Parameter(description = "Идентификатор корзины")
            int cartId,
            @PathVariable
            @Parameter(description = "Идентификатор курса")
            int courseId
    ) {
        cartService.deleteCourseFromCart(cartId, courseId);
    }

    @DeleteMapping("/{cartId}")
    @Operation(
            summary = "Очищение корзины",
            description = "Удаление всех курсов из базы данных корзины, идентификатор которой передан в строке запроса"
    )
    public void clearCart(
            @PathVariable
            @Parameter(description = "Идентификатор корзины")
            int cartId
    ) {
        cartService.clearCart(cartId);
    }
}
