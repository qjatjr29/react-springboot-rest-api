package com.example.beommin.domain.orders.repository;

import com.example.beommin.Utils;
import com.example.beommin.domain.orders.entity.user.Manager;
import com.example.beommin.domain.orders.entity.user.Person;
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
public class ManagerRepository{

    private static final Logger logger = LoggerFactory.getLogger(ManagerRepository.class);

    private static final String INSERT_SQL = "INSERT INTO managers(user_id, email, password, name, address, phone_number, created_at) VALUES (UUID_TO_BIN(:userId), :email, :password, :name, :address, :phoneNumber, :createdAt)";
    private static final String UPDATE_BY_ID_SQL = "UPDATE managers SET name = :name, address = :address, phone_number = :phoneNumber WHERE user_id = UUID_TO_BIN(:userId)";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM managers WHERE user_id = UUID_TO_BIN(:userId)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM managers ORDER BY created_at DESC";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM managers WHERE user_id = UUID_TO_BIN(:userId)";
    private static final String SELECT_BY_EMAIL = "SELECT * FROM managers WHERE email = :email";
    private static final String SELECT_BY_LOGIN_OPTION = "SELECT * FROM managers WHERE email = :email AND password = :password";

    private final RowMapper<Manager> managerRowMapper = (resultSet, i) -> {
        UUID userId = Utils.toUUID(resultSet.getBytes("user_id"));
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        String name = resultSet.getString("name");
        String address = resultSet.getString("address");
        String phoneNumber = resultSet.getString("phone_number");
        LocalDate createdAt = resultSet.getTimestamp("created_at").toLocalDateTime().toLocalDate();
        return new Manager(userId, email, password, address, name, phoneNumber, createdAt);
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ManagerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Manager insert(Manager manager) {
        int insert = jdbcTemplate.update(INSERT_SQL, manager.toMap());
        if(insert != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return manager;
    }

    public List<Manager> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, managerRowMapper);
    }

    public Optional<Manager> findByManagerId(UUID userId) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, Collections.singletonMap("userId", userId.toString().getBytes()), managerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.info("해당 데이터가 없습니다.");
            return Optional.empty();
        }
    }

    public Optional<Manager> findByEmail(String email) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_EMAIL, Collections.singletonMap("email", email), managerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Manager> login(String email, String password) {
        Map<String, Object> paramMap = new HashMap<>() {{
            put("email", email);
            put("password", password);
        }};
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_LOGIN_OPTION, paramMap, managerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.info("해당 데이터가 없습니다.");
            return Optional.empty();
        }
    }

    public Manager update(Manager manager) {
        int update = jdbcTemplate.update(UPDATE_BY_ID_SQL, manager.toMap());
        if(update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return manager;
    }

    public Manager deleteById(Manager manager) {
        jdbcTemplate.update(DELETE_BY_ID_SQL, manager.toMap());
        return manager;
    }
}
