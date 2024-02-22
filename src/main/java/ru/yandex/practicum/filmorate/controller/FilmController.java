package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class FilmController {
    private Map<Integer, Film> films = new HashMap<>();
    int nextID = 1;

    @PostMapping(value = "/films")
    public Film create(@RequestBody Film film) throws ValidationException {
        try {
            validate(film);
        } catch (ValidationException exc) {
            log.error(exc.getMessage());
            throw new ValidationException(exc.getMessage());
        }
        Film newFilm = Film.builder()
                .id(nextID++)
                .name(film.getName())
                .description(film.getDescription())
                .releaseDate(film.getReleaseDate())
                .duration(film.getDuration()).build();
        films.put(newFilm.getId(), newFilm);
        log.info("Добавлен фильм: '{}'", newFilm.getName());
        return newFilm;
    }

    @GetMapping(value = "/films")
    public Collection<Film> getAllFilms() {
        return films.values();
    }

    @PutMapping(value = "/films")
    Film update(@RequestBody Film film) throws ValidationException {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Обновлён фильм: '{}'", film.getName());
            return film;
        } else {
            log.error("Фильм не найден");
            throw new ValidationException("Фильм не найден");
        }
    }

    private void validate(Film film) throws ValidationException {
        if (film.getName() == null || film.getName().isBlank() || film.getName().isEmpty()) {
            throw new ValidationException("Название фильма не должно быть пустым");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() < 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
    }
}
