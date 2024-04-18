package de.aittr.online_lessons.domain.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.aittr.online_lessons.services.CartService;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

/**
 * Class describing the cart.
 * CartService is provided for working with cart objects {@link CartService}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "cart")
public class Cart {

    /**
     * Cart ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    /**
     * Courses added to cart
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "cart_course",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @ToString.Exclude
    private List<Course> courses = new ArrayList<>();

    /**
     * User who owns the cart
     */
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "account_id")
    private User user;

    /**
     * Adding a course to cart
     *
     * @param course Course
     */
    public void addCourse(Course course) {
        courses.add(course);
    }

    /**
     * Removing a course from cart
     *
     * @param course Course
     */
    public void removeCourse(Course course) {
        courses.removeIf(c -> c.getId() == course.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id) && Objects.equals(courses, cart.courses) && Objects.equals(user, cart.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courses, user);
    }
}
