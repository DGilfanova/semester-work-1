package ru.kpfu.itis.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {
    private UUID reservationNumber;
    private User user;
    private Room room;
    private Timestamp arrivalDate;
    private Timestamp departureDate;
}
