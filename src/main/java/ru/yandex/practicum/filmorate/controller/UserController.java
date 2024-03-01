package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.List;

@RestController
@Slf4j
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/users")
    public User create(@RequestBody User user) {
        User newUser = userService.create(user);
        log.info("Создан пользователь: '{}'", newUser.getName());
        return newUser;
    }

    @GetMapping("/users")
    public Collection<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    User get(@PathVariable long id) {
        log.info("Запрошен пользователь: id = ", id);
        return userService.get(id);
    }


    @PutMapping("/users")
    User update(@RequestBody User user) {
        User updatedUser = userService.update(user);
        return updatedUser;
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId) {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable long id, @PathVariable long friendId) {
        userService.removeFriend(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    List<User> getFriends(@PathVariable long id) {
        log.info("Запрошены друзья пользователя: id = {}", id);
        return userService.getFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    List<User> getCommonFriends(@PathVariable long id, @PathVariable long otherId) {
        log.info("Запрошены общие друзья пользователей: id = {}, id = {}", id, otherId);
        return userService.getCommonFriends(id, otherId);
    }

}

