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

//    @Pattern(
//            regexp = "[A-ZА-Яa-zа-я\\s]{5,}",
//            message = "The title field must contain only letters and be a minimum of 5 characters."
//    )
    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String photoPath;

    @NotNull
    @NotBlank
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
