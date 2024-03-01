package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Data
@Builder
public class User {
    private long id;
    private final String email;
    private final String login;
    private String name;
    private final LocalDate birthday;
    private Set<Long> friends;
    public void addFriend(Long id) {
        friends.add(id);
    }
    public void removeFriend(Long id) {
        friends.remove(id);
    }
    public boolean isFriend(Long id){
        return friends.contains(id);
    }


}
