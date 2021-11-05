package ru.kpfu.itis.services;

import ru.kpfu.itis.entities.Room;
import ru.kpfu.itis.repositories.RoomsRepository;

import java.util.List;

public class RoomServiceImpl implements RoomService {

    private RoomsRepository roomsRepository;

    public RoomServiceImpl(RoomsRepository roomsRepository) {
        this.roomsRepository = roomsRepository;
    }

    @Override
    public List<Room> findRooms() {
        return roomsRepository.findAll();
    }
}
