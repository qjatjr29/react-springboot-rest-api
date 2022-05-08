package com.example.beommin.domain.orders.repository;

import com.example.beommin.Utils;
import com.example.beommin.domain.orders.entity.Order;
import com.example.beommin.domain.orders.entity.OrderItem;
import com.example.beommin.domain.orders.entity.OrderStatus;
import com.example.beommin.domain.orders.entity.food.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class OrderRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcFoodRepository.class);
    private static final String INSERT_SQL = "INSERT INTO orders(order_id, user_id,  name, address, phone_number, order_status, price, created_at, updated_at) VALUES (UUID_TO_BIN(:orderId), UUID_TO_BIN(:userId), :name, :address, :phoneNumber, :orderStatus, :price, :createdAt, :updatedAt)";
    private static final String UPDATE_BY_ID_SQL = "UPDATE orders SET name = :name, address = :address, phone_number = :phoneNumber, order_status = :orderStatus, updated_at = :updatedAt WHERE order_id = UUID_TO_BIN(:orderID)";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM orders WHERE order_id = UUID_TO_BIN(:orderId)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM orders ORDER BY created_at DESC";
    private static final String SELECT_BY_USER_ID_SQL = "SELECT * FROM orders WHERE user_id = UUID_TO_BIN(:userId) ORDER BY created_at DESC";
    private static final String SELECT_BY_ORDER_ID_SQL = "SELECT * FROM orders WHERE order_id = UUID_TO_BIN(:orderId)";

    private final RowMapper<Order> orderMapper = (resultSet, i) -> {
        UUID orderId = Utils.toUUID(resultSet.getBytes("order_id"));
        UUID userId = Utils.toUUID(resultSet.getBytes("user_id"));
        String name = resultSet.getString("name");
        String phoneNumber = resultSet.getString("phone_number");
        String address = resultSet.getString("address");
        Integer price = resultSet.getInt("price");
        OrderStatus orderStatus = OrderStatus.valueOf(resultSet.getString("order_status"));
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updatedAt = resultSet.getTimestamp("updated_at").toLocalDateTime();

        return new Order(orderId, userId, name, phoneNumber, address, price, orderStatus,createdAt, updatedAt);
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Order insert(Order order) {
        jdbcTemplate.update(INSERT_SQL, order.toMap());
        return order;
    }


    public List<Order> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, orderMapper);
    }


    public Optional<Order> findById(UUID orderId) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ORDER_ID_SQL, Collections.singletonMap("orderId", orderId.toString().getBytes()), orderMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.info("해당 데이터가 없습니다.");
            return Optional.empty();
        }
    }

    public List<Order> findByUserId(UUID userId) {
        return jdbcTemplate.query(SELECT_BY_USER_ID_SQL, Collections.singletonMap("userId", userId.toString().getBytes()), orderMapper);
    }

    public Order update(Order order) {
        int update = jdbcTemplate.update(UPDATE_BY_ID_SQL, order.toMap());
        if(update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return order;
    }

    public Order deleteById(Order order) {
        jdbcTemplate.update(DELETE_BY_ID_SQL, order.toMap());
        return order;
    }


}
