package com.example.beommin.domain.orders.repository;

import com.example.beommin.Utils;
import com.example.beommin.domain.orders.entity.Order;
import com.example.beommin.domain.orders.entity.OrderItem;
import com.example.beommin.domain.orders.entity.OrderList;
import com.example.beommin.domain.orders.entity.food.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class OrderListRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcFoodRepository.class);

    private static final String INSERT_SQL = "INSERT INTO order_lists(order_list_id, name, address, phone_number, updated_at)" +
            " VALUE (UUID_TO_BIN(:orderListId), :name, :address, :phoneNumber, :updatedAt)";
    private static final String UPDATE_BY_ID_SQL = "UPDATE order_lists SET name = :name, address = :address, phone_number = :phoneNumber, updated_at = :updatedAt WHERE order_list_id = UUID_TO_BIN(:orderListId)";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM order_lists WHERE order_list_id = UUID_TO_BIN(:orderListId)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM order_lists";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM order_lists WHERE order_list_id = UUID_TO_BIN(:orderListId)";
//    private static final String SELECT_BY_CUSTOMER_SQL = "SELECT * FROM orderItems WHERE customer_id = :customerId";

    private final RowMapper<OrderList> orderListMapper = (resultSet, i) -> {
        UUID orderListId = Utils.toUUID(resultSet.getBytes("order_list_id"));
        String name = resultSet.getString("name");
        String address = resultSet.getString("address");
        String phoneNumber = resultSet.getString("phone_number");
        LocalDateTime updatedAt = resultSet.getTimestamp("updated_at").toLocalDateTime();
        return new OrderList(orderListId, name, phoneNumber, address, updatedAt);
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderListRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public OrderList insert(OrderList orderList) {

        jdbcTemplate.update(INSERT_SQL, orderList.toMap());
        return orderList;
    }


    public List<OrderList> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, orderListMapper);
    }


    public Optional<OrderList> findByOrderListId(UUID orderListId) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, Collections.singletonMap("orderListId", orderListId.toString().getBytes()), orderListMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.info("해당 데이터가 없습니다.");
            return Optional.empty();
        }
    }

    public OrderList deleteOrderList(OrderList orderList)  {
        jdbcTemplate.update(DELETE_BY_ID_SQL, orderList.toMap());
        return orderList;
    }

}
