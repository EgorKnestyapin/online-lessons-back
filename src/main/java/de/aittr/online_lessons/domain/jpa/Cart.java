package de.aittr.online_lessons.domain.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @ManyToMany
    @JoinTable(
            name = "cart_course",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courseList = new ArrayList<>();

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    public Cart() {
    }

    public Cart(int id, List<Course> courseList, User user) {
        this.id = id;
        this.courseList = courseList;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addCourse(Course course) {
        courseList.add(course);
    }

    public boolean removeCourse(Course course) {
        return courseList.removeIf(c -> c.getId() == course.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return id == cart.id && Objects.equals(courseList, cart.courseList) && Objects.equals(user, cart.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseList, user);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", courseList=" + courseList +
                ", user=" + user +
                '}';
    }
}
