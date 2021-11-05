package ru.kpfu.itis.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.kpfu.itis.entities.Order;
import ru.kpfu.itis.entities.Suggestion;
import ru.kpfu.itis.entities.User;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OrdersRepositoryJdbcTemplateImpl implements OrdersRepository {

    private final static String SQL_INSERT = "insert into orders (user_uuid, suggestion_id, date) values (?::uuid, ?, ?)";

    private final static String SQL_UPDATE = "update orders set date=? where id=?";

    private final static String SQL_DELETE = "delete from orders where id=?";

    private final static String SQL_SELECT_NEW_BY_USER_ID = "select o.id as id, o.user_uuid as user_uuid, suggestion_id, name, price, " +
            "date from orders o join suggestion s on o.suggestion_id = s.id where date>? and o.user_uuid=?::uuid";

    private final static String SQL_SELECT_OLD_BY_USER_ID = "select o.id as id, o.user_uuid as user_uuid, suggestion_id, name, price, " +
            "date from orders o join suggestion s on o.suggestion_id = s.id where date<? and o.user_uuid=?::uuid";

    private final static String SQL_SELECT_ALL_BY_USER_ID = "select o.id as id, o.user_uuid as user_uuid, suggestion_id, name, price, " +
            "date from orders o join suggestion s on o.suggestion_id = s.id where o.user_uuid=?::uuid";

    private final static String SQL_SELECT_ORDER_BY_ID = "select o.id as id, o.user_uuid as user_uuid, suggestion_id, name, price, " +
            "date from orders o join suggestion s on o.suggestion_id = s.id where o.id=?";

    private final static String SQL_SELECT_SAME_TIME_ORDER = "select * from orders where suggestion_id=? and " +
            "(date + INTERVAL '0:30:0' DAY TO SECOND) > ?::timestamp and " +
            "(date - INTERVAL '0:30:0' DAY TO SECOND) < ?::timestamp";

    private JdbcTemplate jdbcTemplate;

    public OrdersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<Order> orderRowMapper = (row, rowNumber) -> Order.builder()
            .id(row.getInt("id"))
            .user(User.builder().uuid(UUID.fromString(row.getString("user_uuid"))).build())
            .suggestion(Suggestion.builder()
                    .id(row.getInt("suggestion_id"))
                    .name(row.getString("name"))
                    .price(row.getInt("price"))
                    .build())
            .date(row.getTimestamp("date"))
            .build();

    private final RowMapper<Order> simpleOrderRowMapper = (row, rowNumber) -> Order.builder()
            .id(row.getInt("id"))
            .user(User.builder().uuid(UUID.fromString(row.getString("user_uuid"))).build())
            .suggestion(Suggestion.builder().id(row.getInt("suggestion_id")).build())
            .date(row.getTimestamp("date"))
            .build();

    @Override
    public void save(Order order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[] {"id"});

            statement.setString(1, order.getUser().getUuid().toString());
            statement.setInt(2, order.getSuggestion().getId());
            statement.setTimestamp(3, order.getDate());

            return statement;
        }, keyHolder);

        order.setId(keyHolder.getKey().intValue());
    }

    @Override
    public void update(Order order) {
        jdbcTemplate.update(SQL_UPDATE, order.getDate(), order.getId());
    }

    @Override
    public void delete(Integer orderId) {
        jdbcTemplate.update(SQL_DELETE, orderId);
    }

    @Override
    public List<Order> findNewByUserUuid(String  userUuid, Timestamp date) {
        return jdbcTemplate.query(SQL_SELECT_NEW_BY_USER_ID, orderRowMapper, date, userUuid);
    }

    @Override
    public List<Order> findOldByUserUuid(String userUuid, Timestamp date) {
        return jdbcTemplate.query(SQL_SELECT_OLD_BY_USER_ID, orderRowMapper, date, userUuid);
    }

    @Override
    public List<Order> findAll(String  userUuid) {
        return jdbcTemplate.query(SQL_SELECT_ALL_BY_USER_ID, orderRowMapper, userUuid);
    }

    @Override
    public Optional<Order> findById(Integer orderId) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_ORDER_BY_ID, orderRowMapper, orderId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Order> findSameTimeOrder(Integer suggestionId, Timestamp date) {
        return jdbcTemplate.query(SQL_SELECT_SAME_TIME_ORDER, simpleOrderRowMapper, suggestionId, date, date);
    }
}
