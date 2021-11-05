package ru.kpfu.itis.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.kpfu.itis.entities.Room;

import javax.sql.DataSource;
import java.util.List;

public class RoomsRepositoryJdbcTemplateImpl implements RoomsRepository {

    private final static String SQL_SELECT_ROOMS = "select * from room";

    private JdbcTemplate jdbcTemplate;

    public RoomsRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<Room> roomRowMapper = (row, rowNumber) -> Room.builder()
            .number(row.getInt("number"))
            .name(row.getString("name"))
            .price(row.getInt("price"))
            .description(row.getString("description"))
            .photoReference(row.getString("photo_refer"))
            .build();

    @Override
    public List<Room> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ROOMS, roomRowMapper);
    }
}
