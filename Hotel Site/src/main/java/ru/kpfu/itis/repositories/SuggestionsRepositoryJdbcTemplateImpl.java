package ru.kpfu.itis.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.kpfu.itis.entities.Suggestion;
import ru.kpfu.itis.entities.User;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

public class SuggestionsRepositoryJdbcTemplateImpl implements SuggestionsRepository {

    private final static String SQL_SELECT_ALL = "select * from suggestion";

    private JdbcTemplate jdbcTemplate;

    public SuggestionsRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<Suggestion> suggestionRowMapper = (row, rowNumber) -> Suggestion.builder()
            .id(row.getInt("id"))
            .name(row.getString("name"))
            .user(User.builder().uuid(UUID.fromString(row.getString("user_uuid"))).build())
            .description(row.getString("description"))
            .price(row.getInt("price"))
            .photoRefer(row.getString("photo_refer"))
            .build();

    @Override
    public List<Suggestion> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, suggestionRowMapper);
    }
}
