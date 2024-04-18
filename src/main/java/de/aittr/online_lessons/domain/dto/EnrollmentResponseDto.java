package de.aittr.online_lessons.domain.dto;

import de.aittr.online_lessons.domain.jpa.Course;
import de.aittr.online_lessons.domain.jpa.Enrollment;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Class describing the enrollment response DTO {@link Enrollment}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class EnrollmentResponseDto {

    /**
     * Enrollment ID
     */
    private int id;

    /**
     * Date of registration for the course
     */
    private LocalDateTime enrollmentDate;

    /**
     * Enrollment status
     */
    private String status;

    /**
     * Course the user has enrolled in
     */
    private Course course;

    /**
     * Getter
     *
     * @return Enrollment ID
     * @see EnrollmentResponseDto#id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter
     *
     * @param id Enrollment ID
     * @see EnrollmentResponseDto#id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter
     *
     * @return Date of registration for the course
     * @see EnrollmentResponseDto#enrollmentDate
     */
    public LocalDateTime getEnrollmentDate() {
        return enrollmentDate;
    }

    /**
     * Setter
     *
     * @param enrollmentDate Date of registration for the course
     * @see EnrollmentResponseDto#enrollmentDate
     */
    public void setEnrollmentDate(LocalDateTime enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    /**
     * Getter
     *
     * @return Enrollment status
     * @see EnrollmentResponseDto#status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter
     *
     * @param status Enrollment status
     * @see EnrollmentResponseDto#status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Getter
     *
     * @return Course the user has enrolled in
     * @see EnrollmentResponseDto#course
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Setter
     *
     * @param course Course the user has enrolled in
     * @see EnrollmentResponseDto#course
     */
    public void setCourse(Course course) {
        this.course = course;
    }
}
