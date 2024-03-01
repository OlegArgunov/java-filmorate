package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;

import java.time.LocalDate;
import java.util.Set;

/**
 * Film.
 */
@Data
@Builder
public class Film {
    private final long id;
    private final String name;
    private final String description;
    private final LocalDate releaseDate;
    private final double duration;
    private Set<Long> likes;
    private long rate;

    public void addLike(long userID) {
        likes.add(userID);
        rate = likes.size();
    }

    public void removeLike(long userID) {
        if (!likes.contains(userID))
            throw new ObjectNotFoundException("Пользователь не найден");
        likes.remove(userID);
        rate = likes.size();
    }


}