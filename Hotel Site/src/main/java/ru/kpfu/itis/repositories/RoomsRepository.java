package ru.kpfu.itis.repositories;

import ru.kpfu.itis.entities.Room;

import java.util.List;

public interface RoomsRepository {

    List<Room> findAll();
}
