//package de.aittr.online_lessons.services.mapping;
//
//import de.aittr.online_lessons.domain.dto.CourseDto;
//import de.aittr.online_lessons.domain.jpa.Course;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CourseMappingService {
//
//    public Course mapDtoToEntity(CourseDto courseDto) {
//        int id = courseDto.getId();
//        String name = courseDto.getTitle();
//        int price = courseDto.getPrice();
//        String description = courseDto.getDescription();
//        return new Course(id, name, price, description);
//    }
//
//    public CourseDto mapEntityToDto(Course course) {
//        int id = course.getId();
//        String name = course.getTitle();
//        int price = course.getPrice();
//        String description = course.getDescription();
//        return new CourseDto(id, name, price, description);
//    }
//}

package de.aittr.online_lessons.mapping;

import de.aittr.online_lessons.domain.dto.CourseDto;
import de.aittr.online_lessons.domain.jpa.Course;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

/**
 * Service containing tools for course mapping {@link Course}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@Mapper(componentModel = "spring")
public interface CourseMappingService {
    Course mapDtoToEntity(CourseDto dto);

    CourseDto mapEntityToDto(Course course);

    Set<CourseDto> mapSetEntityToSetDto(Set<Course> courses);

    Set<Course> mapSetDtoToSetEntity(Set<CourseDto> courses);

    List<Course> mapListDtoToListEntity(List<CourseDto> courses);
}