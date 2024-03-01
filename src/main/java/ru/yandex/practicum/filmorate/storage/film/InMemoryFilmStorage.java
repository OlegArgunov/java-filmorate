package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private Map<Long, Film> films = new HashMap<>();
    private int nextID = 1;

    @Override
    public Film create(Film film) {
        Film newFilm = Film.builder()
                .id(nextID++)
                .name(film.getName())
                .description(film.getDescription())
                .releaseDate(film.getReleaseDate())
                .likes(new HashSet<>())
                .rate(0)
                .duration(film.getDuration()).build();
        films.put(newFilm.getId(), newFilm);
        return newFilm;
    }

    @Override
    public Film update(Film film) {
        if (films.containsKey(film.getId())) {
            Film updatedFilm = Film.builder()
                    .id(film.getId())
                    .name(film.getName())
                    .description(film.getDescription())
                    .releaseDate(film.getReleaseDate())
                    .likes(new HashSet<>())
                    .rate(0)
                    .duration(film.getDuration()).build();
            films.put(updatedFilm.getId(), updatedFilm);
            return updatedFilm;
        } else {
            return null;
        }
    }


    @Override
    public void delete(int id) {

    }

    @Override
    public List<Film> getAllFilms() {
        return List.copyOf(films.values());
    }

    @Override
    public Film get(Long id) {
        return films.get(id);
    }
}
