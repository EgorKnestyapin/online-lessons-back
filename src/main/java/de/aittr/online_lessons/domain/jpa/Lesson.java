package de.aittr.online_lessons.domain.jpa;


import de.aittr.online_lessons.services.LessonService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

/**
 * Class describing the lesson.
 * LessonService is provided for working with lesson objects {@link LessonService}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@Schema(description = "Lesson entity")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "lesson")
public class Lesson {

    /**
     * Lesson ID
     */
    @Schema(description = "Lesson unique identifier", example = "12")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Lesson title
     */
    @Schema(description = "Lesson name", example = "Data science")
    @Column(name = "title")
    @NotNull
    private String title;

    /**
     * Path to the photo
     */
    @Schema(
            description = "Storage path of the lesson photo",
            example = "https://online-lessons-files.fra1.digitaloceanspaces.com/photos/GJGKvfw.jpg"
    )
    @Column(name = "photo_path")
    @NotNull
    private String photoPath;

    /**
     * Lesson content
     */
    @Schema(
            description = "Additional educational material",
            example = "In this lesson we looked at the technology..."
    )
    @Column(name = "content", length = 1800)
    @NotNull
    private String content;

    /**
     * Lesson serial number
     */
    @Schema(
            description = "Lesson serial number",
            example = "3"
    )
    @Column(name = "number")
    @NotNull
    private int number;

    /**
     * Course the lesson belongs to
     */
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "course_id")
    private Course course;

    /**
     * Getter
     *
     * @return Lesson ID
     * @see Lesson#id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter
     *
     * @param id Lesson ID
     * @see Lesson#id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter
     *
     * @return Lesson title
     * @see Lesson#title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter
     *
     * @param title Lesson title
     * @see Lesson#title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter
     *
     * @return Path to the photo
     * @see Lesson#photoPath
     */
    public String getPhotoPath() {
        return photoPath;
    }

    /**
     * Setter
     *
     * @param photoPath Path to the photo
     * @see Lesson#photoPath
     */
    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    /**
     * Getter
     *
     * @return Course the lesson belongs to
     * @see Lesson#course
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Setter
     *
     * @param course Course the lesson belongs to
     * @see Lesson#course
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * Getter
     *
     * @return Lesson content
     * @see Lesson#content
     */
    public String getContent() {
        return content;
    }

    /**
     * Setter
     *
     * @param content Lesson content
     * @see Lesson#content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Getter
     *
     * @return Lesson serial number
     * @see Lesson#number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Setter
     *
     * @param number Lesson serial number
     * @see Lesson#number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return number == lesson.number && Objects.equals(id, lesson.id) && Objects.equals(title, lesson.title)
                && Objects.equals(photoPath, lesson.photoPath) && Objects.equals(content, lesson.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, photoPath, content, number);
    }
}
