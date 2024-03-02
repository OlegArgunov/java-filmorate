package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@Slf4j
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping("/films")
    public Film create(@RequestBody Film film) {
        Film newFilm = filmService.create(film);
        log.info("Добавлен фильм: '{}'", newFilm.getName());
        return newFilm;
    }

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        return filmService.getAllFilms();
    }

    @GetMapping("/films/{id}")
    Film get(@PathVariable long id) {
        log.info("Запрошен фильм: id = {}", id);
        return filmService.get(id);
    }

    @PutMapping("/films")
    Film update(@RequestBody Film film) {
        Film updatedFilm = filmService.update(film);
        log.info("Обновлён фильм: '{}'", updatedFilm.getName());
        return updatedFilm;
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(@PathVariable long id, @PathVariable long userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void removeLike(@PathVariable long id, @PathVariable long userId) {
        filmService.removeLike(id, userId);
    }

    @GetMapping("/films/popular")
    public List<Film> getBest(@RequestParam(defaultValue = "10") int count) {
        return filmService.getBest(count);
    }

}
