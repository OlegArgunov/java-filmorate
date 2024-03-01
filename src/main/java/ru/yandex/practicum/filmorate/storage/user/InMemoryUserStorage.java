package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private Map<Long, User> users = new HashMap<>();
    private int nextID = 1;

    @Override
    public User create(User user) {
        User newUser = User.builder()
                .id(nextID++)
                .name(user.getName())
                .login(user.getLogin())
                .email(user.getEmail())
                .birthday(user.getBirthday())
                .friends(new HashSet<>())
                .build();
        users.put(newUser.getId(), newUser);
        return newUser;

    }

    @Override
    public User update(User user) {
        if (users.containsKey(user.getId())) {
            User updatedUser = User.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .login(user.getLogin())
                    .email(user.getEmail())
                    .birthday(user.getBirthday())
                    .friends(new HashSet<>())
                    .build();

            users.put(updatedUser.getId(), updatedUser);
            return updatedUser;
        } else {
            return null;
        }
    }

    @Override
    public void delete(int id) {

    }
    @Override
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @Override
    public User get(Long id) {
        return users.get(id);
    }

}
