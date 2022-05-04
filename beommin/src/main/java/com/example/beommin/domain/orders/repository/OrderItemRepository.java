package com.example.beommin.domain.orders.repository;

import com.example.beommin.Utils;
import com.example.beommin.domain.orders.entity.Order;
import com.example.beommin.domain.orders.entity.OrderItem;
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
public class OrderItemRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcFoodRepository.class);

    private static final String INSERT_SQL = "INSERT INTO order_items(order_id, food_id, category, price, quantity)" +
            " VALUE (UUID_TO_BIN(:orderId), UUID_TO_BIN(:foodId), :category, :price, :quantity)";
    private static final String UPDATE_BY_ID_SQL = "UPDATE order_items SET price = :price, quantity = :quantity WHERE order_id = UUID_TO_BIN(:orderId) AND food_id = UUID_TO_BIN(:foodId)";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM order_items WHERE order_id = UUID_TO_BIN(:orderId) AND food_id = UUID_TO_BIN(:foodId)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM order_items";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM order_items WHERE order_id = UUID_TO_BIN(:orderId)";
//    private static final String SELECT_BY_CUSTOMER_SQL = "SELECT * FROM orderItems WHERE customer_id = :customerId";

    private final RowMapper<OrderItem> orderItemMapper = (resultSet, i) -> {
        UUID orderId = Utils.toUUID(resultSet.getBytes("order_id"));
        UUID foodId = Utils.toUUID(resultSet.getBytes("food_id"));
        Integer price = resultSet.getInt("price");
        Integer quantity = resultSet.getInt("quantity");
        String category = resultSet.getString("category");

        return new OrderItem(foodId, Category.getCategoryType(category), price, quantity);
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderItemRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public OrderItem insert(OrderItem orderItem, UUID orderId) {

        jdbcTemplate.update(INSERT_SQL, orderItem.toMap(orderId));
        return orderItem;
    }


    public List<OrderItem> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, orderItemMapper);
    }


    public List<OrderItem> findByOrderId(UUID orderId) {
        return jdbcTemplate.query(SELECT_BY_ID_SQL, Collections.singletonMap("orderId", orderId.toString().getBytes()), orderItemMapper);
    }

    public OrderItem update(OrderItem orderItem, UUID orderId) {
        int update = jdbcTemplate.update(UPDATE_BY_ID_SQL, orderItem.toMap(orderId));
        if(update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return orderItem;
    }

    public OrderItem deleteById(OrderItem orderItem, UUID orderId) {
        jdbcTemplate.update(DELETE_BY_ID_SQL, orderItem.toMap(orderId));
        return orderItem;
    }

}
