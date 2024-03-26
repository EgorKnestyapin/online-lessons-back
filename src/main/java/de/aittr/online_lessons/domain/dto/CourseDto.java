package de.aittr.online_lessons.domain.dto;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class CourseDto {
    private int id;
    private String title;
    private int price;
    private String description;
    private int authorId;
}
