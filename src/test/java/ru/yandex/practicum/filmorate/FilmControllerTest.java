package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exceptions.CheckException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.time.LocalDate;
import java.util.HashSet;

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
                .likes(new HashSet<>())
                .duration(0).build();
        FilmController filmController = new FilmController(new FilmService(new InMemoryFilmStorage()));
        assertEquals(film, filmController.create(film));
    }

    @Test
    void checkFilmName() {
        Film film = Film.builder()
                .duration(0).build();
        FilmController filmController = new FilmController(new FilmService(new InMemoryFilmStorage()));

        final CheckException exception = assertThrows(
                CheckException.class,
                new Executable() {
                    @Override
                    public void execute() throws CheckException {
                        filmController.create(film);
                    }
                });
        assertEquals("Название фильма не должно быть пустым", exception.getMessage());
    }

}
