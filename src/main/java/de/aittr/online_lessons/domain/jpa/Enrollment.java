package de.aittr.online_lessons.domain.jpa;

import de.aittr.online_lessons.services.CourseService;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class describing the enrollment.
 * CourseService is provided for working with enrollment objects {@link CourseService}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "enrollment")
public class Enrollment {

    /**
     * Enrollment ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Date of registration for the course
     */
    @Column(name = "enrollment_date")
    @NotNull
    private LocalDateTime enrollmentDate;

    /**
     * Enrollment status
     */
    @Column(name = "status")
    @NotNull
    private String status;

    /**
     * User who signed up for the course
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "account_id")
    private User user;

    /**
     * Course the user has enrolled in
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "course_id")
    private Course course;

    /**
     * Getter
     *
     * @return Enrollment ID
     * @see Enrollment#id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter
     *
     * @param id Enrollment ID
     * @see Enrollment#id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter
     *
     * @return Date of registration for the course
     * @see Enrollment#enrollmentDate
     */
    public LocalDateTime getEnrollmentDate() {
        return enrollmentDate;
    }

    /**
     * Setter
     *
     * @param enrollmentDate Date of registration for the course
     * @see Enrollment#enrollmentDate
     */
    public void setEnrollmentDate(LocalDateTime enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    /**
     * Getter
     *
     * @return Enrollment status
     * @see Enrollment#status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter
     *
     * @param status Enrollment status
     * @see Enrollment#status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Getter
     *
     * @return User who signed up for the course
     * @see Enrollment#user
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter
     *
     * @param user User who signed up for the course
     * @see Enrollment#user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Getter
     *
     * @return Course the user has enrolled in
     * @see Enrollment#course
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Setter
     *
     * @param course Course the user has enrolled in
     * @see Enrollment#course
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enrollment that = (Enrollment) o;
        return Objects.equals(id, that.id) && Objects.equals(enrollmentDate, that.enrollmentDate)
                && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, enrollmentDate, status);
    }
}
