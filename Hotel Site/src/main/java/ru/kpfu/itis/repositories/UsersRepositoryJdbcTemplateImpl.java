package ru.kpfu.itis.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.kpfu.itis.entities.User;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {

    private final static String SQL_SELECT_BY_EMAIL = "select * from users where email=?";

    private final static String SQL_INSERT = "insert into users (uuid, first_name, last_name, patronymic, passport_number," +
            "mobile_number, email, hash_password) values (?::uuid, ?, ?, ?, ?, ?, ?, ?)";

    private final static String SQL_SELECT_BY_UUID = "select * from users where uuid = ?::uuid";

    private final static String SQL_SELECT_WORKER_BY_SUGGESTION = "select uuid, first_name, last_name, patronymic, " +
            "passport_number, mobile_number, email, hash_password, role from suggestion s join users u on s.user_uuid=u.uuid where s.id=? and role='worker'";

    private final static String SQL_UPDATE_USER_BY_EMAIL = "update users set first_name=?, last_name=?, patronymic=?, " +
            "passport_number=?, mobile_number=? where email=?";

    private JdbcTemplate jdbcTemplate;

    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<User> userRowMapper = (row, rowNumber) -> User.builder()
            .uuid(UUID.fromString(row.getString("uuid")))
            .firstName(row.getString("first_name"))
            .lastName(row.getString("last_name"))
            .patronymic(row.getString("patronymic"))
            .passportNumber(row.getString("passport_number"))
            .mobileNumber(row.getString("mobile_number"))
            .email(row.getString("email"))
            .hashPassword(row.getString("hash_password"))
            .role(row.getString("role"))
            .build();

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_BY_EMAIL, userRowMapper, email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(User user) {
        jdbcTemplate.update(SQL_INSERT, user.getUuid().toString(), user.getFirstName(), user.getLastName(),
                user.getPatronymic(), user.getPassportNumber(), user.getMobileNumber(),user.getEmail(), user.getHashPassword());
    }

    @Override
    public Optional<User> findByUuid(String uuid) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_BY_UUID, userRowMapper, uuid));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findBySuggestionId(Integer suggestionId) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_WORKER_BY_SUGGESTION, userRowMapper, suggestionId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update(SQL_UPDATE_USER_BY_EMAIL, user.getFirstName(), user.getLastName(), user.getPatronymic(),
                            user.getPassportNumber(), user.getMobileNumber(), user.getEmail());
    }
}
