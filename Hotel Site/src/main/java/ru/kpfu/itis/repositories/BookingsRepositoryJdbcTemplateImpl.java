package ru.kpfu.itis.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.kpfu.itis.entities.Booking;
import ru.kpfu.itis.entities.Room;
import ru.kpfu.itis.entities.User;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BookingsRepositoryJdbcTemplateImpl implements BookingsRepository {

    private final static String SQL_INSERT = "insert into booking (reservation_number, user_uuid, " +
            "room_number, arrival_date, departure_date) values (?::uuid, ?::uuid, ?, ?, ?)";

    private final static String SQL_SELECT_BY_RESERVATION_NUMBER = "select * from booking b join users u on b.user_uuid = u.uuid where reservation_number=?::uuid";

    private final static String SQL_SELECT_SAME_TIME_BOOKING = "select * from booking b join room r on r.number = b.room_number " +
            "where r.number=? and ((?>b.arrival_date and ?<b.departure_date) or (?<b.departure_date and ?>b.arrival_date))";

    private final static String SQL_SELECT_BOOKING_SATISFYING_ORDER_DATE = "select * from booking where user_uuid = ?::uuid " +
            "and ?::timestamp < departure_date and ?::timestamp > arrival_date";

    private JdbcTemplate jdbcTemplate;

    public BookingsRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<Booking> bookingRowMapper = (row, rowNumber) -> Booking.builder()
            .reservationNumber(UUID.fromString(row.getString("reservation_number")))
            .user(User.builder()
                    .uuid(UUID.fromString(row.getString("user_uuid")))
                    .firstName(row.getString("first_name"))
                    .lastName(row.getString("last_name"))
                    .patronymic(row.getString("patronymic"))
                    .passportNumber(row.getString("passport_number"))
                    .email(row.getString("email"))
                    .build())
            .room(Room.builder().number(row.getInt("room_number")).build())
            .arrivalDate(row.getTimestamp("arrival_date"))
            .departureDate(row.getTimestamp("departure_date"))
            .build();

    @Override
    public void save(Booking booking) {
        jdbcTemplate.update(SQL_INSERT, booking.getReservationNumber(), booking.getUser().getUuid().toString(),
                booking.getRoom().getNumber(), booking.getArrivalDate(), booking.getDepartureDate());
    }

    @Override
    public Optional<Booking> findByReservationNumber(String reservationNumber) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_BY_RESERVATION_NUMBER, bookingRowMapper, reservationNumber));
        }
        catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Booking> findSameTimeBooking(Integer roomNumber, Timestamp arrivalDate, Timestamp departureDate) {
        return jdbcTemplate.query(SQL_SELECT_SAME_TIME_BOOKING, bookingRowMapper, roomNumber, arrivalDate, arrivalDate, departureDate, departureDate);
    }

    @Override
    public List<Booking> findBookingSatisfyingOrderDate(String userUuid, Timestamp orderDate, Timestamp currentDate) {
        return jdbcTemplate.query(SQL_SELECT_BOOKING_SATISFYING_ORDER_DATE, bookingRowMapper, userUuid, orderDate, currentDate);
    }
}
