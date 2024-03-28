package de.aittr.online_lessons.domain.jpa;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

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
    private Integer id;

    @Column(name = "title")
    @NotNull
    private String title;

    @Column(name = "price")
    @NotNull
    private int price;

    @Column(name = "photo_path")
    @NotNull
    private String photoPath;

    @Column(name = "presentation_path")
    private String presentationPath;

    @Column(name = "description", length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id) && price == course.price && Objects.equals(title, course.title) && Objects.equals(description, course.description) && Objects.equals(user, course.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, price, description, user);
    }
}
