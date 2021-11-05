package ru.kpfu.itis.repositories;

import ru.kpfu.itis.entities.User;

import java.util.Optional;

public interface UsersRepository {

    Optional<User> findByEmail(String email);
    void save(User user);
    Optional<User> findByUuid(String  uuid);
    Optional<User> findBySuggestionId(Integer suggestionId);
    void update(User user);
}
