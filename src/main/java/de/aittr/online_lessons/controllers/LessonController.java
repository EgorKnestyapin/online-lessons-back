package de.aittr.online_lessons.controllers;

import de.aittr.online_lessons.controllers.api.LessonApi;
import de.aittr.online_lessons.domain.dto.LessonDto;
import de.aittr.online_lessons.services.LessonService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class LessonController implements LessonApi {
    private final LessonService service;

    public LessonController(LessonService service) {
        this.service = service;
    }

    @Override
    public LessonDto createLesson(LessonDto lessonDto, int courseId) {
        return service.save(lessonDto, courseId);
    }

    @Override
    public List<LessonDto> getAll() {
        return service.getAllLessons();
    }

    @Override
    public Set<LessonDto> getCreatedLessons(int courseId) {
        return service.getLessonsByCourseId(courseId);
    }

    @Override
    public LessonDto updateCourse(int lessonId, LessonDto lessonDto) {
        return service.updateById(lessonDto, lessonId);
    }

    @Override
    public void deleteCourse(int lessonId) {
        service.deleteById(lessonId);
    }
}
