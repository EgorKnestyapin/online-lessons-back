package de.aittr.online_lessons.services;

import de.aittr.online_lessons.domain.dto.LessonDto;
import de.aittr.online_lessons.domain.jpa.Course;
import de.aittr.online_lessons.domain.jpa.Lesson;
import de.aittr.online_lessons.exception_handling.exceptions.LessonDuplicateException;
import de.aittr.online_lessons.exception_handling.exceptions.LessonNotFoundException;
import de.aittr.online_lessons.exception_handling.exceptions.LessonValidationException;
import de.aittr.online_lessons.mapping.LessonMappingService;
import de.aittr.online_lessons.repositories.jpa.LessonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service containing tools for working with lesson entity {@link Lesson}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@Service
public class LessonService {

    /**
     * {@link LessonRepository}
     */
    private final LessonRepository lessonRepository;

    /**
     * {@link LessonMappingService}
     */
    private final LessonMappingService mappingService;

    /**
     * {@link CourseService}
     */
    private final CourseService courseService;

    /**
     * Constructor for creating lesson service
     *
     * @param lessonRepository Lesson repository
     * @param mappingService   Mapping service for lesson
     * @param courseService    Course service
     */
    public LessonService(LessonRepository lessonRepository, LessonMappingService mappingService, CourseService courseService) {
        this.lessonRepository = lessonRepository;
        this.mappingService = mappingService;
        this.courseService = courseService;
    }

    /**
     * Saving the lesson to the database
     *
     * @param dto      Lesson DTO
     * @param courseId Course ID
     * @return Saved lesson
     * @throws LessonDuplicateException  Lesson with this number already exists
     * @throws LessonValidationException Incorrect values of lesson fields
     */
    public LessonDto save(LessonDto dto, int courseId) {
        Lesson lesson = mappingService.mapDtoToEntity(dto);
        lesson.setId(0);
        Course course = courseService.getCourseEntityById(courseId);
        lesson.setCourse(course);

        List<LessonDto> allLessons = getAllLessons();
        allLessons.forEach(lessonDto -> {
            if (lessonDto.getNumber() == dto.getNumber()) {
                throw new LessonDuplicateException("Lesson with this number already exists");
            }
        });

        try {
            lesson = lessonRepository.save(lesson);
        } catch (Exception e) {
            throw new LessonValidationException("Incorrect values of lesson fields", e);
        }

        return mappingService.mapEntityToDto(lesson);
    }

    /**
     * Getting all lessons from the database
     *
     * @return List of lessons
     */
    public List<LessonDto> getAllLessons() {
        return lessonRepository.findAll().stream()
                .map(mappingService::mapEntityToDto)
                .toList();
    }

    /**
     * Receiving all lessons belonging to the course
     *
     * @param courseId Course ID
     * @return Set of lessons
     */
    public Set<LessonDto> getLessonsByCourseId(int courseId) {
        Course course = courseService.getCourseEntityById(courseId);
        return mappingService.mapSetEntityToSetDto(course.getCreatedLessons());
    }

    /**
     * Receiving a couple of lessons belonging to the course
     *
     * @param courseId Course ID
     * @return Set of lessons
     */
    public Set<LessonDto> getDemoLessonsByCourseId(int courseId) {
        Course course = courseService.getCourseEntityById(courseId);
        Set<Lesson> lessons = course.getCreatedLessons().stream().limit(2).collect(Collectors.toSet());
        return mappingService.mapSetEntityToSetDto(lessons);
    }

    /**
     * Updating lesson fields by ID
     *
     * @param dto      Lesson DTO
     * @param lessonId Lesson ID
     * @return Updated lesson
     * @throws LessonNotFoundException   Lesson not found
     * @throws LessonValidationException Incorrect values of lesson fields
     */
    public LessonDto updateById(LessonDto dto, int lessonId) {
        Lesson foundLesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException("Lesson not found with id " + lessonId));

        Lesson lesson = mappingService.mapDtoToEntity(dto);
        lesson.setId(lessonId);
        lesson.setCourse(foundLesson.getCourse());

        try {
            lesson = lessonRepository.save(lesson);
        } catch (Exception e) {
            throw new LessonValidationException("Incorrect values of lesson fields", e);
        }

        return mappingService.mapEntityToDto(lesson);
    }

    /**
     * Deleting a lesson from the database by ID
     *
     * @param lessonId Lesson ID
     * @throws LessonNotFoundException Lesson not found
     */
    public void deleteById(int lessonId) {
        if (!lessonRepository.existsById(lessonId)) {
            throw new LessonNotFoundException("Lesson not found with id " + lessonId);
        }

        lessonRepository.deleteById(lessonId);
    }
}
