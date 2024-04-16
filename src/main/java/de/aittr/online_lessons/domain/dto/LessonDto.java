package de.aittr.online_lessons.domain.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class LessonDto {

    private int id;

    @Pattern(
            regexp = "(?=.*[A-Za-zА-Яа-я])[A-ZА-Яa-zа-я0-9\\s]{5,80}",
            message = "The title field must contain only letters and be a minimum of 5 characters " +
                    "and a maximum of 80 characters"
    )
    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String photoPath;

    @NotNull
    @NotBlank
    @Length(min = 5, max = 300, message = "The content field must contain minimum 5 and maximum 300 characters.")
    private String content;

    @NotNull
    private int number;

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

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
