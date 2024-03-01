package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.CheckException;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film create(Film film) {
        validate(film);
        return filmStorage.create(film);
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film get(Long id) {
        Film film = filmStorage.get(id);
        if (film == null) {
            throw new ObjectNotFoundException("Фильм не найден");
        }
        return filmStorage.get(id);

    }

    public Film update(Film film) {
        Film updatedFilm = filmStorage.update(film);
        if (updatedFilm == null) {
            throw new ObjectNotFoundException("Фильм не найден");
        }
        return updatedFilm;
    }

    public void addLike(Long id, Long userId) {
        filmStorage.get(id).addLike(userId);
    }

    public void removeLike(Long id, Long userId) {
        if (filmStorage.get(id) == null)
            throw new ObjectNotFoundException("Фильм не найден");
        filmStorage.get(id).removeLike(userId);
    }

    public List<Film> getBest(int count) {
        List<Film> bestFilms = new ArrayList<>(getAllFilms());
        bestFilms.sort((f1, f2) -> {
            if (f2.getRate() > f1.getRate())
                return 1;
            else if (f2.getRate() < f1.getRate())
                return -1;
            else
                return 0;

        });
        return bestFilms.subList(0, Integer.min(count, bestFilms.size()));
    }

    private void validate(Film film) {
        if (film.getName() == null || film.getName().isBlank() || film.getName().isEmpty()) {
            throw new CheckException("Название фильма не должно быть пустым");
        }
        if (film.getDescription().length() > 200) {
            throw new CheckException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new CheckException("Дата релиза — не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() < 0) {
            throw new CheckException("Продолжительность фильма должна быть положительной");
        }
    }
}
