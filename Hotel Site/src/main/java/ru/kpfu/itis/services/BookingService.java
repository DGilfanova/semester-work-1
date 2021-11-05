package ru.kpfu.itis.services;

import ru.kpfu.itis.dto.BookingForm;
import ru.kpfu.itis.dto.RetrieveBookingForm;
import ru.kpfu.itis.entities.Booking;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.UUID;

public interface BookingService {

    void createBooking(BookingForm bookingForm, UUID userUuid, HttpSession session);

    Booking retrieveBooking(RetrieveBookingForm retrieveBookingForm);
}
