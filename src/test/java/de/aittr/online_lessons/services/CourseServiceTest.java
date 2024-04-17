package de.aittr.online_lessons.services;

import de.aittr.online_lessons.domain.dto.CourseDto;
import de.aittr.online_lessons.domain.dto.EnrollmentResponseDto;
import de.aittr.online_lessons.domain.jpa.Course;
import de.aittr.online_lessons.domain.jpa.Enrollment;
import de.aittr.online_lessons.domain.jpa.User;
import de.aittr.online_lessons.exception_handling.exceptions.CartNotFoundException;
import de.aittr.online_lessons.exception_handling.exceptions.CourseNotFoundException;
import de.aittr.online_lessons.exception_handling.exceptions.CourseValidationException;
import de.aittr.online_lessons.mapping.CourseMappingService;
import de.aittr.online_lessons.mapping.EnrollmentMappingService;
import de.aittr.online_lessons.repositories.jpa.CourseRepository;
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
class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseMappingService courseMappingService;

    @Autowired
    private UserService userService;

    @Autowired
    private EnrollmentMappingService enrollmentMappingService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void savePositiveTest() {
        int courseSizeBefore = courseRepository.findAll().size();
        CourseDto courseDto = new CourseDto(0, "Web programming", 0, 330,
                "https://rsv.ru/blog/wp-content/uploads/2021/09/onlajn-kurs-918x516.jpg", null,
                "Embark on a comprehensive exploration of marketing, from its foundational elements to " +
                        "cutting-edge strategies. Begin by understanding market segmentation, targeting, and " +
                        "positioning to tailor your messages effectively. Dive deep into the realm of branding, " +
                        "mastering the art of crafting compelling brand narratives and fostering unwavering brand " +
                        "loyalty. Navigate the dynamic landscape of digital marketing, utilizing powerful tools " +
                        "such as SEO and social media to amplify your brands presence and engage with your audience " +
                        "on a profound level. Delve into the intricacies of traditional marketing channels, honing " +
                        "your skills in storytelling and adapting your approach to resonate with diverse " +
                        "demographics. As you progress, develop strategic prowess by aligning your marketing " +
                        "objectives with emerging market trends and consumer behaviors. Embrace the power of " +
                        "content marketing and analytics, leveraging data-driven insights to optimize your " +
                        "campaigns and drive tangible results. Your journey in marketing will not only enrich your " +
                        "skill set but also empower you to shape the future of brands and businesses in an " +
                        "increasingly competitive landscape.", 0);
        Course expected = courseMappingService.mapDtoToEntity(courseDto);
        CourseDto savedCourse = courseService.save(courseDto, "user123");
        Course actual = courseRepository.findById(savedCourse.getId()).orElse(null);
        User expectedUser = (User) userService.loadUserByUsername("user123");
        int courseSizeAfter = courseRepository.findAll().size();

        assert actual != null;
        Assert.assertEquals((int) (courseDto.getPrice() * 1.2), actual.getOldPrice());
        Assert.assertEquals(expected.getPrice(), actual.getPrice());
        Assert.assertEquals(expected.getTitle(), actual.getTitle());
        Assert.assertEquals(expected.getDescription(), actual.getDescription());
        Assert.assertEquals(expectedUser, actual.getUser());
        Assert.assertEquals(courseSizeBefore + 1, courseSizeAfter);
    }

    @Test
    void saveNegativeTest() {
        CourseDto courseDto = new CourseDto(0, "Web programming", 0, 330,
                "Embark on a comprehensive exploration of marketing, from its foundational elements to " +
                        "cutting-edge strategies. Begin by understanding market segmentation, targeting, and " +
                        "positioning to tailor your messages effectively. Dive deep into the realm of branding, " +
                        "mastering the art of crafting compelling brand narratives and fostering unwavering brand " +
                        "loyalty. Navigate the dynamic landscape of digital marketing, utilizing powerful tools " +
                        "such as SEO and social media to amplify your brands presence and engage with your audience " +
                        "on a profound level. Delve into the intricacies of traditional marketing channels, honing " +
                        "your skills in storytelling and adapting your approach to resonate with diverse " +
                        "demographics. As you progress, develop strategic prowess by aligning your marketing " +
                        "objectives with emerging market trends and consumer behaviors. Embrace the power of " +
                        "content marketing and analytics, leveraging data-driven insights to optimize your " +
                        "campaigns and drive tangible results. Your journey in marketing will not only enrich your " +
                        "skill set but also empower you to shape the future of brands and businesses in an ",
                null, "Embark on a comprehensive exploration of marketing.", 0);
        Exception exception = assertThrows(CourseValidationException.class, () -> {
            courseService.save(courseDto, "user123");
        });
        String expectedMessage = "Incorrect values of course fields";
        String actualMessage = exception.getMessage();

        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void getAllCourses() {
        List<Course> expected = courseRepository.findAll();
        List<CourseDto> actual = courseService.getAllCourses();

        for (int i = 0; i < expected.size(); i++) {
            Assert.assertEquals(expected.get(i), courseMappingService.mapDtoToEntity(actual.get(i)));
        }
    }

    @Test
    void getCourseById() {
        Course expected = courseRepository.findById(3).orElse(null);
        CourseDto actual = courseService.getCourseById(3);

        Assert.assertEquals(expected, courseMappingService.mapDtoToEntity(actual));
    }

    @Test
    void getCourseEntityByIdPositiveTest() {
        Course expected = courseRepository.findById(3).orElse(null);
        Course actual = courseService.getCourseEntityById(3);

        Assert.assertEquals(expected, actual);
    }

    @Test
    void getCourseEntityByIdNegativeTest() {
        Exception exception = assertThrows(CourseNotFoundException.class, () -> {
            courseService.getCourseEntityById(125);
        });
        String expectedMessage = "Course not found with id 125";
        String actualMessage = exception.getMessage();

        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void update() {
        Course foundCourse = courseService.getCourseEntityById(3);
        int oldPrice = foundCourse.getPrice();
        CourseDto courseDto = new CourseDto(0, "Java programming", 0, 150,
                "https://wsltech.com.br/wp-content/uploads/2021/02/photo-1532618403260-5aeffed45f6e-1051x640-2.jpg", null,
                "Embark on a comprehensive exploration of marketing, from its foundational elements to " +
                        "cutting-edge strategies. Begin by understanding market segmentation, targeting, and " +
                        "positioning to tailor your messages effectively. Dive deep into the realm of branding, " +
                        "mastering the art of crafting compelling brand narratives and fostering unwavering brand " +
                        "loyalty. Navigate the dynamic landscape of digital marketing, utilizing powerful tools " +
                        "such as SEO and social media to amplify your brands presence and engage with your audience " +
                        "on a profound level. Delve into the intricacies of traditional marketing channels, honing " +
                        "your skills in storytelling and adapting your approach to resonate with diverse " +
                        "demographics. As you progress, develop strategic prowess by aligning your marketing " +
                        "objectives with emerging market trends and consumer behaviors. Embrace the power of " +
                        "content marketing and analytics, leveraging data-driven insights to optimize your " +
                        "campaigns and drive tangible results. Your journey in marketing will not only enrich your " +
                        "skill set but also empower you to shape the future of brands and businesses in an " +
                        "increasingly competitive landscape.", 0);
        Course expected = courseMappingService.mapDtoToEntity(courseDto);
        CourseDto updatedCourse = courseService.update(3, courseDto);
        Course actual = courseRepository.findById(updatedCourse.getId()).orElse(null);
        System.out.println(foundCourse);

        assert actual != null;
        Assert.assertEquals(oldPrice, actual.getOldPrice());
        Assert.assertEquals(expected.getPrice(), actual.getPrice());
        Assert.assertEquals(expected.getTitle(), actual.getTitle());
        Assert.assertEquals(expected.getDescription(), actual.getDescription());
        Assert.assertEquals(foundCourse.getUser(), actual.getUser());
        Assert.assertEquals(foundCourse.getCreatedLessons(), actual.getCreatedLessons());
        Assert.assertEquals(foundCourse.getEnrollments(), actual.getEnrollments());
    }

    @Test
    void deleteByIdPositiveTest() {
        int courseSizeBefore = courseRepository.findAll().size();
        Course course = courseService.getCourseEntityById(3);

        Assert.assertNotNull(course);

        courseService.deleteById(3);
        int courseSizeAfter = courseRepository.findAll().size();
        Course actual = courseRepository.findById(3).orElse(null);

        Assert.assertNull(actual);
        Assert.assertEquals(courseSizeBefore - 1, courseSizeAfter);
    }

    @Test
    void deleteByIdNegativeTest() {
        Exception exception = assertThrows(CourseNotFoundException.class, () -> {
            courseService.getCourseEntityById(125);
        });
        String expectedMessage = "Course not found with id 125";
        String actualMessage = exception.getMessage();

        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void getEnrollmentsByUsername() {
        Set<EnrollmentResponseDto> enrollments = courseService.getEnrollmentsByUsername("user123");
        User foundUser = (User) userService.loadUserByUsername("user123");
        Set<Enrollment> expected = enrollmentMappingService.mapSetDtoToSetEntity(enrollments);
        Set<Enrollment> actual = foundUser.getEnrollments();

        Assert.assertEquals(expected, actual);
    }

    @Test
    void getCreatedCoursesByUsername() {
        Set<CourseDto> createdCourses = courseService.getCreatedCoursesByUsername("user123");
        User foundUser = (User) userService.loadUserByUsername("user123");
        Set<Course> expected = courseMappingService.mapSetDtoToSetEntity(createdCourses);
        Set<Course> actual = foundUser.getCreatedCourses();

        Assert.assertEquals(expected, actual);
    }
}