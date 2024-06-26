package de.aittr.online_lessons.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * Class for swagger documentation configuration.
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@OpenAPIDefinition(
        info = @Info(
                title = "Online lessons",
                description = "Application for online lessons",
                version = "1.0.0",
                contact = @Contact(
                        name = "Learn",
                        email = "learn@example.com",
                        url = "http://www.learn.com/"
                )
        )
)
public class SwaggerConfig {
}
