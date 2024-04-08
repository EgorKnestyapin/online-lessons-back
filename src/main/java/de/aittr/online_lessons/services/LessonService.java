package de.aittr.online_lessons.services;

import de.aittr.online_lessons.domain.dto.CourseDto;
import de.aittr.online_lessons.domain.dto.LessonDto;
import de.aittr.online_lessons.domain.jpa.Course;
import de.aittr.online_lessons.domain.jpa.Lesson;
import de.aittr.online_lessons.exception_handling.exceptions.CourseNotFoundException;
import de.aittr.online_lessons.exception_handling.exceptions.CourseValidationException;
import de.aittr.online_lessons.exception_handling.exceptions.LessonNotFoundException;
import de.aittr.online_lessons.exception_handling.exceptions.LessonValidationException;
import de.aittr.online_lessons.mapping.LessonMappingService;
import de.aittr.online_lessons.repositories.jpa.LessonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    private final LessonMappingService mappingService;

    private final CourseService courseService;

    public LessonService(LessonRepository lessonRepository, LessonMappingService mappingService, CourseService courseService) {
        this.lessonRepository = lessonRepository;
        this.mappingService = mappingService;
        this.courseService = courseService;
    }

    public LessonDto save(LessonDto dto, int courseId) {
        Lesson lesson = mappingService.mapDtoToEntity(dto);
        lesson.setId(0);
        Course course = courseService.getCourseEntityById(courseId);
        lesson.setCourse(course);

        try {
            lesson = lessonRepository.save(lesson);
        } catch (Exception e) {
            throw new LessonValidationException("Incorrect values of lesson fields", e);
        }

        return mappingService.mapEntityToDto(lesson);
    }

    public List<LessonDto> getAllLessons() {
        return lessonRepository.findAll().stream()
                .map(mappingService::mapEntityToDto)
                .toList();
    }

    public Set<LessonDto> getLessonsByCourseId(int courseId) {
        Course course = courseService.getCourseEntityById(courseId);
        return mappingService.mapSetEntityToSetDto(course.getCreatedLessons());
    }

    public LessonDto updateById(LessonDto dto, int lessonId) {
        Lesson foundLesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException("Lesson not found with id " + lessonId));

        Lesson lesson = mappingService.mapDtoToEntity(dto);
        lesson.setId(lessonId);
        lesson.setCourse(foundLesson.getCourse());

        try {
            lesson = lessonRepository.save(lesson);
        } catch (Exception e) {
            throw new CourseValidationException("Incorrect values of lesson fields", e);
        }

        return mappingService.mapEntityToDto(lesson);
    }

    public void deleteById(int lessonId) {
        if (!lessonRepository.existsById(lessonId)) {
            throw new CourseNotFoundException("Lesson not found with id " + lessonId);
        }

        lessonRepository.deleteById(lessonId);
    }
}
