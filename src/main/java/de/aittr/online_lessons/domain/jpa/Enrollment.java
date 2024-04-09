package de.aittr.online_lessons.domain.jpa;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "enrollment")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "enrollment_date")
    @NotNull
    private LocalDateTime enrollmentDate;

    @Column(name = "status")
    @NotNull
    private String status;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "course_id")
    private Course course;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDateTime enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enrollment that = (Enrollment) o;
        return Objects.equals(id, that.id) && Objects.equals(enrollmentDate, that.enrollmentDate)
                && Objects.equals(status, that.status) && Objects.equals(user, that.user)
                && Objects.equals(course, that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, enrollmentDate, status, user, course);
    }
}
