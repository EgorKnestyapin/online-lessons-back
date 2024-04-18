package de.aittr.online_lessons.domain.dto;

import de.aittr.online_lessons.domain.jpa.Course;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

/**
 * Class describing the course DTO {@link Course}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class CourseDto {

    /**
     * Course ID
     */
    private int id;

    /**
     * Course title
     */
    @Pattern(
            regexp = "[A-ZА-Яa-zа-я\\s]{5,80}",
            message = "The title field must contain only letters and be a minimum of 5 characters and " +
                    "a maximum of 80 characters."
    )
    private String title;

    /**
     * Course previous price
     */
    @Min(value = 0, message = "The price must be positive.")
    @Max(value = 9999, message = "The price cannot be more than 9999.")
    private int oldPrice;

    /**
     * Course current price
     */
    @NotNull
    @Min(value = 0, message = "The price must be positive.")
    @Max(value = 9999, message = "The price cannot be more than 9999.")
    private int price;

    /**
     * Path to the photo
     */
    @NotNull
    @NotBlank
    private String photoPath;

    /**
     * Course description
     */
    @NotNull
    @Length(
            min = 300, max = 1800,
            message = "The description field must contain minimum 300 and maximum 1800 characters."
    )
    private String description;

    /**
     * Number of students enrolled in the course
     */
    private int counter;

    /**
     * Getter
     *
     * @return Course ID
     * @see CourseDto#id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter
     *
     * @param id Course ID
     * @see CourseDto#id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter
     *
     * @return Course title
     * @see CourseDto#title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter
     *
     * @param title Course title
     * @see CourseDto#title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter
     *
     * @return Previous price of the course
     * @see CourseDto#price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Setter
     *
     * @param price Previous price of the course
     * @see CourseDto#price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Getter
     *
     * @return Course description
     * @see CourseDto#description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter
     *
     * @param description Course description
     * @see CourseDto#description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter
     *
     * @return Path to the photo
     * @see CourseDto#photoPath
     */
    public String getPhotoPath() {
        return photoPath;
    }

    /**
     * Setter
     *
     * @param photoPath Path to the photo
     * @see CourseDto#photoPath
     */
    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    /**
     * Getter
     *
     * @return Number of students enrolled in the course
     * @see CourseDto#counter
     */
    public int getCounter() {
        return counter;
    }

    /**
     * Setter
     *
     * @param counter Number of students enrolled in the course
     * @see CourseDto#counter
     */
    public void setCounter(int counter) {
        this.counter = counter;
    }

    /**
     * Getter
     *
     * @return Previous price of the course
     * @see CourseDto#oldPrice
     */
    public int getOldPrice() {
        return oldPrice;
    }

    /**
     * Setter
     *
     * @param oldPrice Previous price of the course
     * @see CourseDto#oldPrice
     */
    public void setOldPrice(int oldPrice) {
        this.oldPrice = oldPrice;
    }
}
