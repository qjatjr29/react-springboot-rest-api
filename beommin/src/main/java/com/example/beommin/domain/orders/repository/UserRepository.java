package com.example.beommin.domain.orders.repository;

import com.example.beommin.Utils;
import com.example.beommin.domain.orders.entity.User;
import com.example.beommin.domain.orders.entity.food.Category;
import com.example.beommin.domain.orders.entity.food.Food;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
@Profile({"dev", "test", "default"})
public class UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    private static final String INSERT_SQL = "INSERT INTO users(user_id, email, password, name, address, phone_number, created_at) VALUES (UUID_TO_BIN(:userId), :email, :password, :name, :address, :phoneNumber, :createdAt)";
    private static final String UPDATE_BY_ID_SQL = "UPDATE users SET name = :name, address = :address, phone_number = :phoneNumber WHERE user_id = UUID_TO_BIN(:userId)";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM users WHERE user_id = UUID_TO_BIN(:userId)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM users ORDER BY created_at DESC";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM users WHERE user_id = UUID_TO_BIN(:userId)";
    private static final String SELECT_BY_EMAIL = "SELECT * FROM users WHERE email = :email";
    private static final String SELECT_BY_LOGIN_OPTION = "SELECT * FROM users WHERE email = :email AND password = :password";

    private final RowMapper<User> userRowMapper = (resultSet, i) -> {
        UUID userId = Utils.toUUID(resultSet.getBytes("user_id"));
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        String name = resultSet.getString("name");
        String address = resultSet.getString("address");
        String phoneNumber = resultSet.getString("phone_number");
        LocalDate createdAt = resultSet.getTimestamp("created_at").toLocalDateTime().toLocalDate();
        return new User(userId, email, password, name, address, phoneNumber, createdAt);
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User insert(User user) {
        int insert = jdbcTemplate.update(INSERT_SQL, user.toMap());
        if(insert != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return user;
    }

    public List<User> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, userRowMapper);
    }

    public Optional<User> findByUserId(UUID userId) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, Collections.singletonMap("userId", userId.toString().getBytes()), userRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.info("해당 데이터가 없습니다.");
            return Optional.empty();
        }
    }

    public Optional<User> findByEmail(String email) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_EMAIL, Collections.singletonMap("email", email), userRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<User> login(String email, String password) {
        Map<String, Object> paramMap = new HashMap<>() {{
            put("email", email);
            put("password", password);
        }};
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_LOGIN_OPTION, paramMap, userRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.info("해당 데이터가 없습니다.");
            return Optional.empty();
        }
    }

    public User update(User user) {
        int update = jdbcTemplate.update(UPDATE_BY_ID_SQL, user.toMap());
        if(update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return user;
    }

    public User deleteById(User user) {
        jdbcTemplate.update(DELETE_BY_ID_SQL, user.toMap());
        return user;
    }
}
