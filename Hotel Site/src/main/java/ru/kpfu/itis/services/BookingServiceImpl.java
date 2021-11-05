package ru.kpfu.itis.services;

import ru.kpfu.itis.dto.BookingForm;
import ru.kpfu.itis.dto.RetrieveBookingForm;
import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.exceptions.EmptyFieldException;
import ru.kpfu.itis.exceptions.InvalidEnteredDataException;
import ru.kpfu.itis.exceptions.NoSuchElementInBaseException;
import ru.kpfu.itis.entities.Booking;
import ru.kpfu.itis.helpers.constants.Constants;
import ru.kpfu.itis.helpers.processors.InfoProcessor;
import ru.kpfu.itis.repositories.BookingsRepository;
import ru.kpfu.itis.repositories.UsersRepository;
import ru.kpfu.itis.helpers.notifiers.MailMessageTemplate;
import ru.kpfu.itis.helpers.notifiers.Notifier;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BookingServiceImpl implements BookingService {

    private BookingsRepository bookingsRepository;

    private UsersRepository usersRepository;

    private Notifier notifier;

    private MailMessageTemplate mailMessageTemplate;

    private InfoProcessor bookingInfoProcessor;

    public BookingServiceImpl(BookingsRepository bookingsRepository, UsersRepository usersRepository, Notifier notifier,
                              MailMessageTemplate mailMessageTemplate, InfoProcessor bookingInfoProcessor) {
        this.bookingsRepository = bookingsRepository;
        this.usersRepository = usersRepository;
        this.notifier = notifier;
        this.mailMessageTemplate = mailMessageTemplate;
        this.bookingInfoProcessor = bookingInfoProcessor;
    }

    @Override
    public void createBooking(BookingForm bookingForm, UUID userUuid, HttpSession session) {
        Booking booking = (Booking) bookingInfoProcessor.process(bookingForm);

        List<Booking> sameBookings = bookingsRepository.findSameTimeBooking(booking.getRoom().getNumber(), booking.getArrivalDate(), booking.getDepartureDate());
        if (sameBookings.size() > 0) {
            throw new InvalidEnteredDataException("This number is occupied for the time you have chosen, please select another number or time.");
        }

        UUID reservationNumber = UUID.randomUUID();
        booking.setReservationNumber(reservationNumber.toString());

        booking.setUser(User.builder().uuid(userUuid).build());

        bookingsRepository.save(booking);

        String userEmail = usersRepository.findByUuid(userUuid.toString()).get().getEmail();

        notifier.notify(Constants.RECEPTION_MAIL, mailMessageTemplate.createAddBookingText(booking.getReservationNumber().toString()));
        notifier.notify(userEmail, mailMessageTemplate.createUserBookingText(booking.getReservationNumber().toString()));
    }

    @Override
    public Booking retrieveBooking(RetrieveBookingForm retrieveBookingForm) {
        if (retrieveBookingForm.getReservationNumber().equals("") || retrieveBookingForm.getEmail().equals("")
                || retrieveBookingForm.getLastName().equals("") || retrieveBookingForm.getReservationNumber().length() > 36) {
            throw new EmptyFieldException("Please fill all fields.");
        }

        Optional<Booking> optionalBooking = bookingsRepository.findByReservationNumber(retrieveBookingForm.getReservationNumber());
        if (!optionalBooking.isPresent()) {
            throw new NoSuchElementInBaseException("There is no booking with this number. Please double check.");
        }

        Booking booking = optionalBooking.get();
        if (!booking.getUser().getEmail().equals(retrieveBookingForm.getEmail())
                || !booking.getUser().getLastName().equals(retrieveBookingForm.getLastName())) {
            throw new InvalidEnteredDataException("Check your last name and email.");
        }

        return booking;
    }
}
