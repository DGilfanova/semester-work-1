package ru.kpfu.itis.helpers.processors;

import ru.kpfu.itis.dto.BookingForm;
import ru.kpfu.itis.entities.Booking;
import ru.kpfu.itis.entities.Room;
import ru.kpfu.itis.exceptions.EmptyFieldException;
import ru.kpfu.itis.exceptions.InvalidEnteredDataException;
import ru.kpfu.itis.helpers.constants.Constants;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookingInfoProcessor implements InfoProcessor<Booking, BookingForm> {

    @Override
    public Booking process(BookingForm bookingForm) {
        Booking booking = Booking.builder()
                .room(Room.builder().number(processRoom(bookingForm.getRoomNumber())).build())
                .build();

        processBookingDate(booking, bookingForm.getArrivalDate(), bookingForm.getDepartureDate());
        return booking;
    }

    private Integer processRoom(String roomNumber) {
        if (roomNumber.equals("")) {
            throw new EmptyFieldException("Please choose room.");
        }

        return Integer.valueOf(roomNumber);
    }

    private void processBookingDate(Booking booking, String arrivalDate, String departureDate) {
        if (arrivalDate.equals("") || departureDate.equals("")) {
            throw new EmptyFieldException("Please enter arrival and departure date.");
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.BOOKING_DATE_FORMAT);

        Timestamp newArrivalDate;
        Timestamp newDepartureDate;

        try {
            newArrivalDate = new Timestamp(simpleDateFormat.parse(arrivalDate).getTime());
            newDepartureDate = new Timestamp(simpleDateFormat.parse(departureDate).getTime());
        } catch (ParseException e) {
            throw new InvalidEnteredDataException("Choose date from calendar, don't write yourself.", e);
        }

        if (newArrivalDate.before(new Date())) {
            throw new InvalidEnteredDataException("Don't choose date in the past.");
        }

        if (newArrivalDate.after(newDepartureDate)) {
            throw new InvalidEnteredDataException("Arrival date later then departure date.");
        }

        if ((newDepartureDate.getTime() - newArrivalDate.getTime())/Constants.DAY_IN_MS > 31) {
            throw new InvalidEnteredDataException("We can book you only for a month.");
        }

        booking.setArrivalDate(newArrivalDate);
        booking.setDepartureDate(newDepartureDate);
    }
}
