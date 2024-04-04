package de.aittr.online_lessons.controllers.api;

import de.aittr.online_lessons.domain.dto.CourseDto;
import de.aittr.online_lessons.security.sec_dto.AuthInfo;
import de.aittr.online_lessons.validation.dto.CartNotFoundErrorDto;
import de.aittr.online_lessons.validation.dto.ForbiddenErrorDto;
import de.aittr.online_lessons.validation.dto.UserNotAuthenticatedErrorDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tags(
        @Tag(name = "Cart controller", description = "Controller for some operations with cart")
)
@RequestMapping("/api/cart")
public interface CartApi {
    @GetMapping("/{cartId}")
    @Operation(
            summary = "Getting courses in the cart",
            description = "Retrieving courses from the cart database whose ID is passed in the query string"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Getting a list of courses in the cart",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = CourseDto.class)))
            ),
            @ApiResponse(responseCode = "403",
                    description = "Access is denied",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ForbiddenErrorDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "Cart not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserNotAuthenticatedErrorDto.class))),
    })
    List<CourseDto> getCourses(
            @PathVariable
            @Parameter(description = "Cart ID")
            int cartId
    );

    @PutMapping("/add/{cartId}/{courseId}")
    @Operation(
            summary = "Adding a course to cart",
            description = "Saving a course to the cart database using the identifier passed in the query string"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Course added to cart"),
            @ApiResponse(responseCode = "403",
                    description = "Access is denied",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ForbiddenErrorDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "Cart not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CartNotFoundErrorDto.class))),
            @ApiResponse(responseCode = "409",
                    description = "Course is already in the cart",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CartNotFoundErrorDto.class))),
    })
    void addCourseToCart(
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
    void deleteCourseFromCart(
            @PathVariable
            @Parameter(description = "Идентификатор корзины")
            int cartId,
            @PathVariable
            @Parameter(description = "Идентификатор курса")
            int courseId
    );

    @DeleteMapping("/clear/{cartId}")
    @Operation(
            summary = "Очищение корзины",
            description = "Удаление всех курсов из базы данных корзины, идентификатор которой передан в строке запроса"
    )
    void clearCart(
            @PathVariable
            @Parameter(description = "Идентификатор корзины")
            int cartId
    );

    @PutMapping("/buy/{cartId}")
    @Operation(
            summary = "Покупка всех курсов в корзине",
            description = "Добавление новой записи в базу данных таблицы enrollment и очищение корзины"
    )
    void buyCourses(
            @PathVariable
            @Parameter(description = "Идентификатор корзины")
            int cartId
    );
}
