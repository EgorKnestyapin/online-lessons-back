package de.aittr.online_lessons.domain.dto;

import de.aittr.online_lessons.domain.jpa.Role;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UserDto {
    private int id;
    private String nickname;
    private String email;
    private String password;
    private Set<Role> roles = new HashSet<>();

    public UserDto() {
    }

    public UserDto(int id, String nickname, String email, String password, Set<Role> roles) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return id == userDto.id && Objects.equals(nickname, userDto.nickname) && Objects.equals(email, userDto.email) && Objects.equals(password, userDto.password) && Objects.equals(roles, userDto.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickname, email, password, roles);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
