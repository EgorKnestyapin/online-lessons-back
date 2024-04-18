package de.aittr.online_lessons.controllers;

import de.aittr.online_lessons.controllers.api.LessonApi;
import de.aittr.online_lessons.domain.dto.LessonDto;
import de.aittr.online_lessons.services.LessonService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * Controller that accepts requests related to lesson {@link LessonService}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@RestController
public class LessonController implements LessonApi {

    /**
     * {@link LessonService}
     */
    private final LessonService service;

    /**
     * Constructor for creating lesson controller
     *
     * @param service Lesson service
     */
    public LessonController(LessonService service) {
        this.service = service;
    }

    /**
     * Creating new lesson
     *
     * @param lessonDto Lesson DTO
     * @param courseId  Course ID
     * @return Created lesson
     */
    @Override
    public LessonDto createLesson(LessonDto lessonDto, int courseId) {
        return service.save(lessonDto, courseId);
    }

    /**
     * Getting all lessons
     *
     * @return List of lessons
     */
    @Override
    public List<LessonDto> getAll() {
        return service.getAllLessons();
    }

    /**
     * Receiving created lessons for a specific course
     *
     * @param courseId Course ID
     * @return Set of lessons
     */
    @Override
    public Set<LessonDto> getCreatedLessons(int courseId) {
        return service.getLessonsByCourseId(courseId);
    }

    /**
     * Receiving a couple of lessons for a specific course
     *
     * @param courseId Course ID
     * @return Set of lessons
     */
    @Override
    public Set<LessonDto> getDemoLessons(int courseId) {
        return service.getDemoLessonsByCourseId(courseId);
    }

    /**
     * Updating a lesson
     *
     * @param lessonId  Lesson ID
     * @param lessonDto Lesson DTO
     * @return Updated lesson
     */
    @Override
    public LessonDto updateCourse(int lessonId, LessonDto lessonDto) {
        return service.updateById(lessonDto, lessonId);
    }

    /**
     * Deleting a lesson
     *
     * @param lessonId Lesson ID
     */
    @Override
    public void deleteCourse(int lessonId) {
        service.deleteById(lessonId);
    }
}
