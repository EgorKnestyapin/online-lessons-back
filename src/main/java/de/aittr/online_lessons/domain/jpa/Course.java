package de.aittr.online_lessons.domain.jpa;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

@Entity
@Table(name = "course")
public class Course{

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

    @Column(name = "description", length=1000)
    @NotNull
    @Length(min = 300, message = "The description field must contain minimum 300 characters.")
    private String description;

    public Course() {
    }

    public Course(int id, String title, int price, String description) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id && Double.compare(course.price, price) == 0 && Objects.equals(title, course.title) && Objects.equals(description, course.description);
    }

    public int hashCode() {
        return Objects.hash(id, title, price, description);
    }

    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
