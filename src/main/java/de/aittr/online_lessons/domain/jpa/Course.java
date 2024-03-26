package de.aittr.online_lessons.domain.jpa;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    @NotNull
    @NotBlank
    @Pattern(
            regexp = "[A-ZА-Яa-zа-я]{5,}",
            message = "The title field must contain only letters and be a minimum of 5 characters."
    )
    private String title;

    @Column(name = "price")
    @NotNull
    @Max(value = 9999, message = "The price cannot be more than 9999.")
    private int price;

    @Column(name = "description", length = 1000)
    @NotNull
    @Length(min = 300, message = "The description field must contain minimum 300 characters.")
    private String description;

    @Column(name = "author_id")
    @NotNull
    private int authorId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id && price == course.price && authorId == course.authorId && Objects.equals(title, course.title) && Objects.equals(description, course.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, price, description, authorId);
    }
}
