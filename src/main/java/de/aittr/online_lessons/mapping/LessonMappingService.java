package de.aittr.online_lessons.mapping;

import de.aittr.online_lessons.domain.dto.LessonDto;
import de.aittr.online_lessons.domain.jpa.Lesson;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

/**
 * Service containing tools for lesson mapping {@link Lesson}
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@Mapper(componentModel = "spring")
public interface LessonMappingService {

    Lesson mapDtoToEntity(LessonDto dto);

    LessonDto mapEntityToDto(Lesson lesson);

    Set<LessonDto> mapSetEntityToSetDto(Set<Lesson> lessons);

    Set<Lesson> mapSetDtoToSetEntity(Set<LessonDto> lessons);

    List<Lesson> mapListDtoToListEntity(List<LessonDto> lessons);
}
