package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class UserController {
    private Map<Integer, User> users = new HashMap<>();
    private int nextID = 1;

    @PostMapping(value = "/users")
    public User create(@RequestBody User user) throws ValidationException {
        validate(user);
        user.setId(nextID++);
        users.put(user.getId(), user);
        log.info("Создан пользователь: '{}'", user.getName());
        return user;
    }

    @GetMapping(value = "/users")
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @PutMapping(value = "/users")
    User update(@RequestBody User user) throws ValidationException {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("Изменён пользователь: '{}'", user.getName());
            return user;
        } else {
            log.error("Пользователь не найден");
            throw new ValidationException("Пользователь не найден");
        }
    }

    private void validate(User user) throws ValidationException {
        String errMessage = null;
        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            errMessage = "Логин не может быть пустым и содержать пробелы";
            log.error(errMessage);
            throw new ValidationException(errMessage);
        }
        if (user.getEmail() == null || user.getEmail().isBlank() || user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            errMessage = "Электронная почта не может быть пустой и должна содержать символ @";
            log.error(errMessage);
            throw new ValidationException(errMessage);
        }
        if (user.getName() == null || user.getName().isBlank() || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday() != null && user.getBirthday().isAfter(LocalDate.now())) {
            errMessage = "Дата рождения не может быть в будущем";
            log.error(errMessage);
            throw new ValidationException(errMessage);
        }
    }
}

