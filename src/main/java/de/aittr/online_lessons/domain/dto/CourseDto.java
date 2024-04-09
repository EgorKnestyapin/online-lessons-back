package de.aittr.online_lessons.domain.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class CourseDto {

    private int id;

    @Pattern(
            regexp = "[A-ZА-Яa-zа-я\\s]{5,}",
            message = "The title field must contain only letters and be a minimum of 5 characters."
    )
    private String title;

    @Min(value = 0, message = "The price must be positive.")
    @Max(value = 9999, message = "The price cannot be more than 9999.")
    private int oldPrice;

    @NotNull
    @Min(value = 0, message = "The price must be positive.")
    @Max(value = 9999, message = "The price cannot be more than 9999.")
    private int price;

    @NotNull
    @NotBlank
    private String photoPath;

    private String presentationPath;

    @NotNull
    @Length(min = 300, message = "The description field must contain minimum 300 characters.")
    private String description;

    private int counter;


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

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getPresentationPath() {
        return presentationPath;
    }

    public void setPresentationPath(String presentationPath) {
        this.presentationPath = presentationPath;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(int oldPrice) {
        this.oldPrice = oldPrice;
    }
}
