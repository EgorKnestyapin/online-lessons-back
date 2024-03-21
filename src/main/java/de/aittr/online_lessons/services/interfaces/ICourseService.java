package de.aittr.online_lessons.services.interfaces;

import de.aittr.online_lessons.domain.dto.CourseDto;
import de.aittr.online_lessons.domain.jpa.Course;

import java.util.List;

public interface ICourseService {
    CourseDto save(CourseDto courseDto);

    List<CourseDto> getAllCourses();

    CourseDto getCourseById(int id);

    void update(CourseDto courseDto);

    void deleteById(int id);

}
