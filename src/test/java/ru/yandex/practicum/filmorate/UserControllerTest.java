package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserControllerTest {

    @Test
    void checkCreateUser() throws ValidationException {
        User user = new User(1, "asd@ya.ru", "login", "Name", LocalDate.now());
        UserController userController = new UserController();
        assertEquals(user, userController.create(user));
    }

    @Test
    void checkUserLogin() {
        User user = new User(1, "asd@ya.ru", "log in", "Name", LocalDate.now());
        UserController userController = new UserController();
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
