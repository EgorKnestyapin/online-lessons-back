package de.aittr.online_lessons.services;

import de.aittr.online_lessons.domain.dto.LessonDto;
import de.aittr.online_lessons.domain.jpa.Lesson;
import de.aittr.online_lessons.exception_handling.exceptions.CartNotFoundException;
import de.aittr.online_lessons.exception_handling.exceptions.CourseNotFoundException;
import de.aittr.online_lessons.mapping.LessonMappingService;
import de.aittr.online_lessons.repositories.jpa.LessonRepository;
import jakarta.transaction.Transactional;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@TestPropertySource(locations = "classpath:application.properties")
class LessonServiceTest {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private LessonMappingService lessonMappingService;

    @BeforeEach
    void setUp() {}

    @Test
    void save() {
        int lessonSizeBefore = lessonRepository.findAll().size();
        LessonDto lessonDto = new LessonDto(0, "Lesson 3",
                "https://www.zastavki.com/pictures/1024x768/2019Girls___Beautyful_Girls_Smart_girl_behind" +
                        "_a_laptop_on_a_gray_background_136491_1.jpg",
                "The From Web Development Basics to Advanced Skills course is designed to equip participants " +
                        "with a comprehensive understanding of web development, covering fundamental concepts as " +
                        "well as advanced techniques. Whether you are starting from scratch or looking to enhance " +
                        "your existing skills, this course offers a structured learning path to master the " +
                        "essentials of web development and advance towards more complex topics.", 3);
        Lesson expected = lessonMappingService.mapDtoToEntity(lessonDto);
        LessonDto savedDto = lessonService.save(lessonDto, 3);
        Lesson actual = lessonMappingService.mapDtoToEntity(savedDto);
        int lessonSizeAfter = lessonRepository.findAll().size();

        Assert.assertEquals(expected.getTitle(), actual.getTitle());
        Assert.assertEquals(expected.getContent(), actual.getContent());
        Assert.assertEquals(expected.getNumber(), actual.getNumber());
        Assert.assertEquals(lessonSizeBefore + 1, lessonSizeAfter);
    }

    @Test
    void getAllLessons() {
        List<LessonDto> allLessons = lessonService.getAllLessons();
        List<Lesson> expected = lessonMappingService.mapListDtoToListEntity(allLessons);
        List<Lesson> actual = lessonRepository.findAll();

        Assert.assertEquals(expected, actual);
    }

    @Test
    void getLessonsByCourseId() {
        Set<LessonDto> lessonsByCourseId = lessonService.getLessonsByCourseId(3);
        Set<Lesson> expected = lessonMappingService.mapSetDtoToSetEntity(lessonsByCourseId);
        Set<Lesson> actual = lessonRepository.findByCourseId(2);

        Assert.assertEquals(expected, actual);
    }

    @Test
    void getDemoLessonsByCourseId() {
        Set<LessonDto> demoLessonsByCourseId = lessonService.getDemoLessonsByCourseId(3);
        Set<Lesson> actual = lessonMappingService.mapSetDtoToSetEntity(demoLessonsByCourseId);

        Assert.assertEquals(0, actual.size());
    }

    @Test
    void updateById() {
        String oldTitle = "LESSON 6";
        int oldNumber = 5;
        Lesson lesson = lessonRepository.findById(17).orElse(null);

        assert lesson != null;
        Assert.assertEquals(oldTitle, lesson.getTitle());
        Assert.assertEquals(oldNumber, lesson.getNumber());

        LessonDto lessonDto = new LessonDto(0, "Lesson 4",
                "https://www.zastavki.com/pictures/1024x768/2019Girls___Beautyful_Girls_Smart_girl_behind" +
                        "_a_laptop_on_a_gray_background_136491_1.jpg",
                "Whether you are starting from scratch or looking to enhance " +
                        "your existing skills, this course offers a structured learning path to master the " +
                        "essentials of web development and advance towards more complex topics.", 4);
        Lesson expected = lessonMappingService.mapDtoToEntity(lessonDto);
        LessonDto updatedDto = lessonService.updateById(lessonDto, 17);
        Lesson actual = lessonMappingService.mapDtoToEntity(updatedDto);

        Assert.assertEquals(expected.getTitle(), actual.getTitle());
        Assert.assertEquals(expected.getNumber(), actual.getNumber());
        Assert.assertEquals(expected.getContent(), actual.getContent());
    }

    @Test
    void deleteByIdPositiveTest() {
        int lessonSizeBefore = lessonRepository.findAll().size();
        Lesson foundLesson = lessonRepository.findById(17).orElse(null);

        Assert.assertNotNull(foundLesson);

        lessonService.deleteById(17);
        int lessonSizeAfter = lessonRepository.findAll().size();
        foundLesson = lessonRepository.findById(17).orElse(null);

        Assert.assertNull(foundLesson);
        Assert.assertEquals(lessonSizeBefore - 1, lessonSizeAfter);
    }

    @Test
    void deleteByIdNegativeTest() {
        Exception exception = assertThrows(CourseNotFoundException.class, () -> {
            lessonService.deleteById(124);
        });
        String expectedMessage = "Lesson not found with id 124";
        String actualMessage = exception.getMessage();

        Assert.assertEquals(expectedMessage, actualMessage);
    }
}