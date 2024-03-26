package de.aittr.online_lessons.domain.jpa;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "user_course")
public class UserCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "enrollment_date")
    @NotNull
    private Date enrollmentDate;
    @Column(name = "status")
    @NotNull
    @NotBlank
    private String status;

    public UserCourse() {
    }

    public UserCourse(int id, Date enrollmentDate, String status) {
        this.id = id;
        this.enrollmentDate = enrollmentDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCourse that = (UserCourse) o;
        return id == that.id && Objects.equals(enrollmentDate, that.enrollmentDate) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, enrollmentDate, status);
    }

    @Override
    public String toString() {
        return "UserCourse{" +
                "id=" + id +
                ", enrollmentDate=" + enrollmentDate +
                ", status='" + status + '\'' +
                '}';
    }
}
