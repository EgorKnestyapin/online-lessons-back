package de.aittr.online_lessons.domain.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.aittr.online_lessons.services.CourseService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.*;

/**
 * Class describing the course.
 * CourseService is provided for working with course objects {@link CourseService}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@Schema(description = "Course entity")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "course")
public class Course {

    /**
     * Course ID
     */
    @Schema(description = "Course unique identifier", example = "14")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Course title
     */
    @Schema(description = "Course name", example = "Data science")
    @Column(name = "title")
    @NotNull
    private String title;

    /**
     * Previous price of the course
     */
    @Schema(description = "Course old price", example = "360")
    @Column(name = "old_price")
    @NotNull
    private int oldPrice;

    /**
     * Current price of the course
     */
    @Schema(description = "Course price", example = "300")
    @Column(name = "price")
    @NotNull
    private int price;

    /**
     * Path to the photo
     */
    @Schema(
            description = "Storage path of the course photo",
            example = "https://online-lessons-files.fra1.digitaloceanspaces.com/photos/GJGKvfw.jpg"
    )
    @Column(name = "photo_path")
    @NotNull
    private String photoPath;

    /**
     * Course description
     */
    @Schema(description = "Course description", example = "This course will cover topics related to...")
    @NotNull
    @Column(name = "description", length = 1800)
    private String description;

    /**
     * Number of students enrolled in the course
     */
    @Schema(description = "Course counter", example = "123")
    @NotNull
    @Column(name = "counter")
    private int counter;

    /**
     * User who created the course
     */
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Course registration records
     */
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private Set<Enrollment> enrollments = new HashSet<>();

    /**
     * Lessons belonging to the course
     */
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Lesson> createdLessons = new LinkedHashSet<>();

    /**
     * Carts to which the course has been added
     */
    @ManyToMany(mappedBy = "courses", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Cart> carts = new ArrayList<>();

    /**
     * Getter
     *
     * @return Course ID
     * @see Course#id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter
     *
     * @param id Course ID
     * @see Course#id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter
     *
     * @return Course title
     * @see Course#title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter
     *
     * @param title Course title
     * @see Course#title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter
     *
     * @return Previous price of the course
     * @see Course#price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Setter
     *
     * @param price Previous price of the course
     * @see Course#price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Getter
     *
     * @return Course description
     * @see Course#description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter
     *
     * @param description Course description
     * @see Course#description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter
     *
     * @return User who created the course
     * @see Course#user
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter
     *
     * @param user User who created the course
     * @see Course#user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Getter
     *
     * @return Path to the photo
     * @see Course#photoPath
     */
    public String getPhotoPath() {
        return photoPath;
    }

    /**
     * Setter
     *
     * @param photoPath Path to the photo
     * @see Course#photoPath
     */
    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    /**
     * Getter
     *
     * @return Number of students enrolled in the course
     * @see Course#counter
     */
    public int getCounter() {
        return counter;
    }

    /**
     * Setter
     *
     * @param counter Number of students enrolled in the course
     * @see Course#counter
     */
    public void setCounter(int counter) {
        this.counter = counter;
    }

    /**
     * Getter
     *
     * @return Previous price of the course
     * @see Course#oldPrice
     */
    public int getOldPrice() {
        return oldPrice;
    }

    /**
     * Setter
     *
     * @param oldPrice Previous price of the course
     * @see Course#oldPrice
     */
    public void setOldPrice(int oldPrice) {
        this.oldPrice = oldPrice;
    }

    /**
     * Getter
     *
     * @return Course registration records
     * @see Course#enrollments
     */
    public Set<Enrollment> getEnrollments() {
        return enrollments;
    }

    /**
     * Setter
     *
     * @param enrollments Course registration records
     * @see Course#enrollments
     */
    public void setEnrollments(Set<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    /**
     * Getter
     *
     * @return Lessons belonging to the course
     * @see Course#createdLessons
     */
    public Set<Lesson> getCreatedLessons() {
        return createdLessons;
    }

    /**
     * Setter
     *
     * @param createdLessons Lessons belonging to the course
     * @see Course#createdLessons
     */
    public void setCreatedLessons(Set<Lesson> createdLessons) {
        this.createdLessons = createdLessons;
    }

    /**
     * Getter
     *
     * @return Carts to which the course has been added
     * @see Course#carts
     */
    public List<Cart> getCarts() {
        return carts;
    }

    /**
     * Setter
     *
     * @param carts Carts to which the course has been added
     * @see Course#carts
     */
    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return oldPrice == course.oldPrice && price == course.price && counter == course.counter
                && Objects.equals(id, course.id) && Objects.equals(title, course.title)
                && Objects.equals(photoPath, course.photoPath) && Objects.equals(description, course.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, oldPrice, price, photoPath, description, counter);
    }
}
