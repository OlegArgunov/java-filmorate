package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.CheckException;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User create(User user) {
        validate(user);
        return userStorage.create(user);
    }

    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User update(User user) {
        User updatedUser = userStorage.update(user);
        if (updatedUser == null) {
            throw new ObjectNotFoundException("Пользователь не найден");
        }
        return updatedUser;
    }

    public User get(Long id) {
        User user = userStorage.get(id);
        if (user == null) {
            throw new ObjectNotFoundException("Пользователь не найден");
        }
        return userStorage.get(id);
    }

    public void addFriend(Long id, Long friendId) {
        if (userStorage.get(id) == null || userStorage.get(friendId) == null) {
            throw new ObjectNotFoundException("Один из друзей не найден");
        }
        userStorage.get(id).addFriend(friendId);
        userStorage.get(friendId).addFriend(id);
    }

    public void removeFriend(Long id, Long friendId) {
        userStorage.get(id).removeFriend(friendId);
        userStorage.get(friendId).removeFriend(id);
    }

    public List<User> getFriends(Long id) {
        return userStorage.get(id).getFriends().stream().map(userId -> userStorage.get(userId)).collect(Collectors.toList());
    }

    public List<User> getCommonFriends(Long id, Long otherId) {
        return getFriends(id).stream().filter(user -> user.isFriend(otherId)).collect(Collectors.toList());
    }

    private void validate(User user) {
        String errMessage = null;
        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            errMessage = "Логин не может быть пустым и содержать пробелы";
            throw new CheckException(errMessage);
        }
        if (user.getEmail() == null || user.getEmail().isBlank() || user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            errMessage = "Электронная почта не может быть пустой и должна содержать символ @";
            throw new CheckException(errMessage);
        }
        if (user.getName() == null || user.getName().isBlank() || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday() != null && user.getBirthday().isAfter(LocalDate.now())) {
            errMessage = "Дата рождения не может быть в будущем";
            throw new CheckException(errMessage);
        }
    }

}
