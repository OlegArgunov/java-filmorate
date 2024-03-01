package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserControllerTest {

    @Test
    void checkCreateUser() throws ValidationException {
        User user = User.builder()
                .id(1)
                .name("Name")
                .login("Login")
                .email("asd@ya.ru")
                .birthday(LocalDate.now())
                .build();
        UserController userController = new UserController(new UserService(new InMemoryUserStorage()));
        assertEquals(user, userController.create(user));
    }

    @Test
    void checkUserLogin() {
        UserController userController = new UserController(new UserService(new InMemoryUserStorage()));
        User user = User.builder()
                .id(1)
                .name("Name")
                .login("Log in")
                .email("asd@ya.ru")
                .birthday(LocalDate.now())
                .build();
        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() throws ValidationException {
                        userController.create(user);
                    }
                });
        assertEquals("Логин не может быть пустым и содержать пробелы", exception.getMessage());
    }
}
