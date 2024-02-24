package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FilmControllerTest {

    @Test
    void contextLoads() {
    }

    @Test
    void checkCreateFile() throws ValidationException {
        Film film = Film.builder()
                .id(1)
                .name("Test")
                .description("Description")
                .releaseDate(LocalDate.now())
                .duration(0).build();
        FilmController filmController = new FilmController();
        assertEquals(film, filmController.create(film));
    }

    @Test
    void checkFilmName() {
        Film film = Film.builder()
                .duration(0).build();
        FilmController filmController = new FilmController();

        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() throws ValidationException {
                        filmController.create(film);
                    }
                });
        assertEquals("Название фильма не должно быть пустым", exception.getMessage());
    }

}
