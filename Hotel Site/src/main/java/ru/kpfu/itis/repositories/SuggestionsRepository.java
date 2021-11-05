package ru.kpfu.itis.repositories;

import ru.kpfu.itis.entities.Suggestion;

import java.util.List;

public interface SuggestionsRepository {
    List<Suggestion> findAll();
}
