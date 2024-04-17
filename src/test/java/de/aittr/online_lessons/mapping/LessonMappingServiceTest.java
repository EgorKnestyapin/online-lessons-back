package de.aittr.online_lessons.mapping;

import de.aittr.online_lessons.domain.dto.LessonDto;
import de.aittr.online_lessons.domain.jpa.Course;
import de.aittr.online_lessons.domain.jpa.Lesson;
import de.aittr.online_lessons.services.CourseService;
import jakarta.transaction.Transactional;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@TestPropertySource(locations = "classpath:application.properties")
class LessonMappingServiceTest {

    @Autowired
    private LessonMappingService lessonMappingService;

    @Autowired
    private CourseService courseService;

    @Test
    void mapDtoToEntity() {
        LessonDto expected = new LessonDto(1, "Lesson 5", "photo-path.com",
                "This lesson about...", 5);
        Lesson actual = lessonMappingService.mapDtoToEntity(expected);

        Assert.assertEquals(expected.getTitle(), actual.getTitle());
        Assert.assertEquals(expected.getContent(), actual.getContent());
        Assert.assertEquals(expected.getPhotoPath(), actual.getPhotoPath());
        Assert.assertEquals(expected.getNumber(), actual.getNumber());
    }

    @Test
    void mapDtoToEntityNull() {
        LessonDto expected = null;
        Lesson actual = lessonMappingService.mapDtoToEntity(expected);

        Assert.assertEquals(null, actual);
    }

    @Test
    void mapEntityToDto() {
        Course foundCourse = courseService.getCourseEntityById(3);
        Lesson expected = new Lesson(1, "Lesson 5", "photo-path.com",
                "This lesson about...", 5, foundCourse);
        LessonDto actual = lessonMappingService.mapEntityToDto(expected);

        Assert.assertEquals(expected.getTitle(), actual.getTitle());
        Assert.assertEquals(expected.getContent(), actual.getContent());
        Assert.assertEquals(expected.getPhotoPath(), actual.getPhotoPath());
        Assert.assertEquals(expected.getNumber(), actual.getNumber());
    }

    @Test
    void mapEntityToDtoNull() {
        Lesson expected = null;
        LessonDto actual = lessonMappingService.mapEntityToDto(expected);

        Assert.assertEquals(null, actual);
    }

    @Test
    void mapSetEntityToSetDto() {
        Course foundCourse = courseService.getCourseEntityById(3);
        Lesson lesson1 = new Lesson(1, "Lesson 5", "photo-path.com",
                "This lesson about...", 5, foundCourse);
        Lesson lesson2 = new Lesson(2, "Lesson 7", "photo-path.com",
                "This lesson about...", 7, foundCourse);
        Lesson lesson3 = new Lesson(3, "Lesson 15", "photo-path.com",
                "This lesson about...", 15, foundCourse);
        Set<Lesson> lessons = Set.of(lesson1, lesson2, lesson3);
        Set<LessonDto> actual = lessonMappingService.mapSetEntityToSetDto(lessons);

        Assert.assertTrue(actual.contains(lessonMappingService.mapEntityToDto(lesson1)));
        Assert.assertTrue(actual.contains(lessonMappingService.mapEntityToDto(lesson2)));
        Assert.assertTrue(actual.contains(lessonMappingService.mapEntityToDto(lesson3)));
    }

    @Test
    void mapSetDtoToSetEntity() {
        LessonDto lesson1 = new LessonDto(1, "Lesson 5", "photo-path.com",
                "This lesson about...", 5);
        LessonDto lesson2 = new LessonDto(2, "Lesson 7", "photo-path.com",
                "This lesson about...", 7);
        LessonDto lesson3 = new LessonDto(3, "Lesson 15", "photo-path.com",
                "This lesson about...", 15);
        Set<LessonDto> lessons = Set.of(lesson1, lesson2, lesson3);
        Set<Lesson> actual = lessonMappingService.mapSetDtoToSetEntity(lessons);

        Assert.assertTrue(actual.contains(lessonMappingService.mapDtoToEntity(lesson1)));
        Assert.assertTrue(actual.contains(lessonMappingService.mapDtoToEntity(lesson2)));
        Assert.assertTrue(actual.contains(lessonMappingService.mapDtoToEntity(lesson3)));
    }
}