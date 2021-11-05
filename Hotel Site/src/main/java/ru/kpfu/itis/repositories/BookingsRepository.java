package ru.kpfu.itis.repositories;

import ru.kpfu.itis.entities.Booking;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface BookingsRepository {

    void save(Booking booking);
    Optional<Booking> findByReservationNumber(String confirmationNumber);
    List<Booking> findSameTimeBooking(Integer roomNumber, Timestamp arrivalDate, Timestamp departureDate);
    List<Booking> findBookingSatisfyingOrderDate(String userUuid, Timestamp orderDate, Timestamp currentDate);
}
