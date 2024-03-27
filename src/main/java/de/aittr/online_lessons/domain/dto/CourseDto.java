package de.aittr.online_lessons.domain.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class CourseDto {

    private int id;

    @Pattern(
            regexp = "[A-ZА-Яa-zа-я]{5,}",
            message = "The title field must contain only letters and be a minimum of 5 characters."
    )
    private String title;

    @NotNull
    @Max(value = 9999, message = "The price cannot be more than 9999.")
    private int price;

    @NotNull
    private String photoPath;

    private String presentationPath;

    @NotNull
    @Length(min = 300, message = "The description field must contain minimum 300 characters.")
    private String description;

    @NotNull
    private int authorId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}
