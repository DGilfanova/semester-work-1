package ru.kpfu.itis.services;

import ru.kpfu.itis.entities.Room;

import java.util.List;

public interface RoomService {

    List<Room> findRooms();
}
