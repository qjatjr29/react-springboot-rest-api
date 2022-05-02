package com.example.beommin.domain.orders.repository;

import com.example.beommin.Utils;
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
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Profile({"dev", "test", "default"})
public class JdbcFoodRepository implements FoodRepository{

    private static final Logger logger = LoggerFactory.getLogger(JdbcFoodRepository.class);

    private static final String INSERT_SQL = "INSERT INTO foods(food_id, name, price, category, sub_category, description, image, store_id) VALUES (UUID_TO_BIN(:foodId), :name, :price, :category, :subCategory, :description, :image, UUID_TO_BIN(:storeId))";
    private static final String UPDATE_BY_ID_SQL = "UPDATE foods SET name = :name, price = :price, sub_category = :subCategory, description = :description WHERE food_id = UUID_TO_BIN(:foodId)";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM foods WHERE food_id = UUID_TO_BIN(:foodId)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM foods";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM foods WHERE food_id = UUID_TO_BIN(:foodId)";
    private static final String SELECT_BY_NAME_SQL = "SELECT * FROM foods WHERE name = :name";
    private static final String SELECT_BY_CATEGORY_SQL = "SELECT * FROM foods WHERE category = :category";

    private final RowMapper<Food> foodRowMapper = (resultSet, i) -> {
        UUID foodId = Utils.toUUID(resultSet.getBytes("food_id"));
        String name = resultSet.getString("name");
        int price = resultSet.getInt("price");
        String category = resultSet.getString("category");
        String subCategory = resultSet.getString("sub_category");
        String description = resultSet.getString("description");
        String image = resultSet.getString("image");
        UUID storeId = Utils.toUUID(resultSet.getBytes("store_id"));

        Category categoryType = Category.getCategoryType(category);
        return categoryType.updateFood(foodId, subCategory, name, price, categoryType, description, image, storeId);
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcFoodRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Food insert(Food food) {
        int insert = jdbcTemplate.update(INSERT_SQL, food.toMap());
        if(insert != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return food;
    }

    @Override
    public List<Food> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, foodRowMapper);
    }

    @Override
    public Optional<Food> findById(UUID foodId) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, Collections.singletonMap("foodId", foodId.toString().getBytes()), foodRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.info("해당 데이터가 없습니다.");
            return Optional.empty();
        }
    }

    @Override
    public Food findByName(String name) {
       return jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, Collections.singletonMap("name", name), foodRowMapper);
    }

    @Override
    public List<Food> findByCategory(Category category) {
        return jdbcTemplate.query(SELECT_BY_CATEGORY_SQL, Collections.singletonMap("category", category.getType()), foodRowMapper);
    }

    @Override
    public Food update(Food food) {
        int update = jdbcTemplate.update(UPDATE_BY_ID_SQL, food.toMap());
        if(update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return food;
    }

    @Override
    public Food deleteById(Food food) {
        jdbcTemplate.update(DELETE_BY_ID_SQL, food.toMap());
        return food;
    }
}
