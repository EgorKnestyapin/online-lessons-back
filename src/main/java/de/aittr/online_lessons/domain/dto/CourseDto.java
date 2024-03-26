package de.aittr.online_lessons.domain.dto;

import java.util.Objects;

public class CourseDto {
    private int id;
    private String title;
    private int price;
    private String description;
    private int authorId;

    public CourseDto(int id, String title, int price, String description, int authorId) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.authorId = authorId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseDto courseDto = (CourseDto) o;
        return id == courseDto.id && price == courseDto.price && authorId == courseDto.authorId && Objects.equals(title, courseDto.title) && Objects.equals(description, courseDto.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, price, description, authorId);
    }

    @Override
    public String toString() {
        return "CourseDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", authorId=" + authorId +
                '}';
    }
}
