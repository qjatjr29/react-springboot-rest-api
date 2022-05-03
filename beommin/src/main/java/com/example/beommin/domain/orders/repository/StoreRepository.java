package com.example.beommin.domain.orders.repository;

import com.example.beommin.Utils;
import com.example.beommin.domain.orders.entity.food.Category;
import com.example.beommin.domain.orders.entity.food.Food;
import com.example.beommin.domain.orders.entity.store.Store;
import com.example.beommin.domain.orders.entity.store.StoreCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile({"dev", "test", "default"})
public class StoreRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcFoodRepository.class);

    private static final String INSERT_SQL = "INSERT INTO stores(store_id, name, address, phone_number, category, image, created_at) VALUES (UUID_TO_BIN(:storeId), :name, :address, :phoneNumber, :category, :image, :createdAt)";
    private static final String UPDATE_BY_ID_SQL = "UPDATE stores SET name = :name, address = :address, phone_number = :phoneNumber WHERE store_id = UUID_TO_BIN(:storeId)";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM stores WHERE store_id = UUID_TO_BIN(:storeId)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM stores";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM stores WHERE store_id = UUID_TO_BIN(:storeId)";
    private static final String SELECT_BY_NAME_SQL = "SELECT * FROM stores WHERE name = :name";
    private static final String SELECT_BY_CATEGORY_SQL = "SELECT * FROM stores WHERE category = :category";

    private final RowMapper<Store> storeRowMapper = (resultSet, i) -> {
        UUID storeId = Utils.toUUID(resultSet.getBytes("store_id"));
        String name = resultSet.getString("name");
        String address = resultSet.getString("address");
        String phoneNumber = resultSet.getString("phone_number");
        String image = resultSet.getString("image");
        String category = resultSet.getString("category");
        StoreCategory storeCategory = StoreCategory.getCategoryType(category);
        LocalDate createdAt = resultSet.getTimestamp("created_at").toLocalDateTime().toLocalDate();

        return storeCategory.createStore(storeId, name, address, phoneNumber, storeCategory, image, createdAt);
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public StoreRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Store insert(Store store) {
        int insert = jdbcTemplate.update(INSERT_SQL, store.toMap());
        if(insert != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return store;
    }


    public List<Store> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, storeRowMapper);
    }


    public Optional<Store> findById(UUID storeId) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, Collections.singletonMap("storeId", storeId.toString().getBytes()), storeRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.info("해당 데이터가 없습니다.");
            return Optional.empty();
        }
    }


    public Store findByName(String name) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, Collections.singletonMap("name", name), storeRowMapper);
    }

    public List<Store> findByCategory(StoreCategory category) {
        return jdbcTemplate.query(SELECT_BY_CATEGORY_SQL, Collections.singletonMap("category", category.getType()), storeRowMapper);
    }

    public Store update(Store store) {
        int update = jdbcTemplate.update(UPDATE_BY_ID_SQL, store.toMap());
        if(update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return store;
    }

    public Store deleteById(Store store) {
        jdbcTemplate.update(DELETE_BY_ID_SQL, store.toMap());
        return store;
    }
}
