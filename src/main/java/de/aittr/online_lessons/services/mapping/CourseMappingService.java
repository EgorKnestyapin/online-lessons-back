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

package de.aittr.online_lessons.services.mapping;

import de.aittr.online_lessons.domain.dto.CourseDto;
import de.aittr.online_lessons.domain.jpa.Course;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
public interface CourseMappingService {
    Course mapDtoToEntity(CourseDto dto);

    CourseDto mapEntityToDto(Course user);
}