package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

/**
 * Film.
 */
@Data
public class User {
    private int id;
    private final String email;
    private final String login;
    private String name;
    private final LocalDate birthday;

    public User(int id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}
