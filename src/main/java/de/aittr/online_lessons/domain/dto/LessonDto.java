package de.aittr.online_lessons.domain.dto;

import de.aittr.online_lessons.domain.jpa.Lesson;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

/**
 * Class describing the lesson DTO {@link Lesson}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class LessonDto {

    /**
     * Lesson ID
     */
    private int id;

    /**
     * Lesson title
     */
    @Pattern(
            regexp = "(?=.*[A-Za-zА-Яа-я])[A-ZА-Яa-zа-я0-9\\s]{5,80}",
            message = "The title field must contain only letters and be a minimum of 5 characters " +
                    "and a maximum of 80 characters"
    )
    @NotNull
    @NotBlank
    private String title;

    /**
     * Path to the photo
     */
    @NotNull
    @NotBlank
    private String photoPath;

    /**
     * Lesson content
     */
    @NotNull
    @NotBlank
    @Length(min = 5, max = 300, message = "The content field must contain minimum 5 and maximum 300 characters.")
    private String content;

    /**
     * Lesson serial number
     */
    @NotNull
    private int number;

    /**
     * Getter
     *
     * @return Lesson ID
     * @see LessonDto#id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter
     *
     * @param id Lesson ID
     * @see LessonDto#id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter
     *
     * @return Lesson title
     * @see LessonDto#title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter
     *
     * @param title Lesson title
     * @see LessonDto#title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter
     *
     * @return Path to the photo
     * @see LessonDto#photoPath
     */
    public String getPhotoPath() {
        return photoPath;
    }

    /**
     * Setter
     *
     * @param photoPath Path to the photo
     * @see LessonDto#photoPath
     */
    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    /**
     * Getter
     *
     * @return Lesson content
     * @see LessonDto#content
     */
    public String getContent() {
        return content;
    }

    /**
     * Setter
     *
     * @param content Lesson content
     * @see LessonDto#content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Getter
     *
     * @return Lesson serial number
     * @see LessonDto#number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Setter
     *
     * @param number Lesson serial number
     * @see LessonDto#number
     */
    public void setNumber(int number) {
        this.number = number;
    }
}
