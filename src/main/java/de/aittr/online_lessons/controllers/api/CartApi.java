package de.aittr.online_lessons.controllers.api;

import de.aittr.online_lessons.domain.dto.CourseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tags(
        @Tag(name = "Cart")
)
@RequestMapping("/api/cart")
public interface CartApi {
    @GetMapping("/{cartId}")
    @Operation(
            summary = "Получение курсов в корзине",
            description = "Получение курсов из базы данных корзины, идентификатор которой передан в строке запроса"
    )
    List<CourseDto> getCourses(
            @PathVariable
            @Parameter(description = "Идентификатор корзины")
            int cartId
    );

    @PutMapping("/{cartId}/{courseId}")
    @Operation(
            summary = "Добавление курса в корзину",
            description = "Сохранение курса в базу данных корзины по идентификатору, переданному в строке запроса"
    )
    void addProductToCart(
            @PathVariable
            @Parameter(description = "Идентификатор корзины")
            int cartId,
            @PathVariable
            @Parameter(description = "Идентификатор курса")
            int courseId
    );

    @DeleteMapping("/{cartId}/{courseId}")
    @Operation(
            summary = "Удаление курса из корзины",
            description = "Удаление курса из базы данных корзины по идентификатору, переданному в строке запроса"
    )
    void deleteProductFromCart(
            @PathVariable
            @Parameter(description = "Идентификатор корзины")
            int cartId,
            @PathVariable
            @Parameter(description = "Идентификатор курса")
            int courseId
    );

    @DeleteMapping("/{cartId}")
    @Operation(
            summary = "Очищение корзины",
            description = "Удаление всех курсов из базы данных корзины, идентификатор которой передан в строке запроса"
    )
    void clearCart(
            @PathVariable
            @Parameter(description = "Идентификатор корзины")
            int cartId
    );
}
