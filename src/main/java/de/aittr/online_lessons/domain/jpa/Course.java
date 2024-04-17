package de.aittr.online_lessons.domain.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.*;

@Schema(description = "Course entity")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "course")
public class Course {

    @Schema(description = "Course unique identifier", example = "14")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Schema(description = "Course name", example = "Data science")
    @Column(name = "title")
    @NotNull
    private String title;

    @Schema(description = "Course old price", example = "360")
    @Column(name = "old_price")
    @NotNull
    private int oldPrice;

    @Schema(description = "Course price", example = "300")
    @Column(name = "price")
    @NotNull
    private int price;

    @Schema(
            description = "Storage path of the course photo",
            example = "https://online-lessons-files.fra1.digitaloceanspaces.com/photos/GJGKvfw.jpg"
    )
    @Column(name = "photo_path")
    @NotNull
    private String photoPath;

    @Schema(
            description = "Storage path of the course presentation",
            example = "https://online-lessons-files.fra1.digitaloceanspaces.com/presentation/Flgssb.pptx"
    )
    @Column(name = "presentation_path")
    private String presentationPath;

    @Schema(description = "Course description", example = "This course will cover topics related to...")
    @NotNull
    @Column(name = "description", length = 1800)
    private String description;

    @Schema(description = "Course counter", example = "123")
    @NotNull
    @Column(name = "counter")
    private int counter;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private Set<Enrollment> enrollments = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Lesson> createdLessons = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "courses", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Cart> carts = new ArrayList<>();

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

    public Set<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Set<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public Set<Lesson> getCreatedLessons() {
        return createdLessons;
    }

    public void setCreatedLessons(Set<Lesson> createdLessons) {
        this.createdLessons = createdLessons;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return oldPrice == course.oldPrice && price == course.price && counter == course.counter &&
                Objects.equals(id, course.id) && Objects.equals(title, course.title) &&
                Objects.equals(photoPath, course.photoPath) &&
                Objects.equals(presentationPath, course.presentationPath)
                && Objects.equals(description, course.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, oldPrice, price, photoPath, presentationPath, description, counter);
    }
}
