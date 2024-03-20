package de.aittr.online_lessons.domain.interfaces;

public interface ICourse {
    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    String getFileName();

    void setFileName(String fileName);

    String getDescription();

    void setDescription(String description);
}
