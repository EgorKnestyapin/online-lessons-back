package de.aittr.online_lessons.domain.dto;

import java.util.Objects;

public class CourseDto {
    private int id;
    private String name;
    private String fileName;
    private String description;

    public CourseDto(int id, String name, String fileName, String description) {
        this.id = id;
        this.name = name;
        this.fileName = fileName;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFileName() {
        return fileName;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseDto courseDto = (CourseDto) o;
        return id == courseDto.id && Objects.equals(name, courseDto.name) && Objects.equals(fileName, courseDto.fileName) && Objects.equals(description, courseDto.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, fileName, description);
    }

    @Override
    public String toString() {
        return "CourseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fileName='" + fileName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
