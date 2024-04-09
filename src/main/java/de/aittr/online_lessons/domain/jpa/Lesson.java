package de.aittr.online_lessons.domain.jpa;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Schema(description = "Lesson entity")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "lesson")
public class Lesson {

    @Schema(description = "Lesson unique identifier", example = "12")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Schema(description = "Lesson name", example = "Data science")
    @Column(name = "title")
    @NotNull
    private String title;

    @Schema(
            description = "Storage path of the lesson photo",
            example = "https://online-lessons-files.fra1.digitaloceanspaces.com/photos/GJGKvfw.jpg"
    )
    @Column(name = "photo_path")
    @NotNull
    private String photoPath;

    @Schema(
            description = "Additional educational material",
            example = "In this lesson we looked at the technology..."
    )
    @Column(name = "content")
    @NotNull
    private String content;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "course_id")
    private Course course;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(id, lesson.id) && Objects.equals(title, lesson.title) && Objects.equals(photoPath, lesson.photoPath) && Objects.equals(course, lesson.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, photoPath, course);
    }
}
