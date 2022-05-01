package com.example.beommin.domain.orders.repository;

import com.example.beommin.domain.orders.entity.food.Category;
import com.example.beommin.domain.orders.entity.food.Food;
import com.example.beommin.domain.orders.entity.food.chicken.FriedChicken;
import com.example.beommin.domain.orders.entity.food.chicken.SpicyChicken;
import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@ActiveProfiles("test")
@DisplayName("Foods table CRUD 테스트!")
class JdbcFoodRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(JdbcFoodRepositoryTest.class);

    @Configuration
    @ComponentScan(basePackages = {"com.example.beommin.domain.orders.repository"})
    static class Config {
        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-jdbc-foods")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
            return dataSource;
        }

        @Bean
        public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }
    }

    @Autowired
    JdbcFoodRepository foodRepository;

    @Autowired
    DataSource dataSource;

    EmbeddedMysql embeddedMysql;

    Food friedChicken, spicyChicken;
    static final UUID friedChickenId = UUID.randomUUID();
    static final UUID spicyChickenId = UUID.randomUUID();

    @BeforeEach
    void setup() {
        friedChicken = new FriedChicken(friedChickenId, "황금올리브", 17000, Category.CHICKEN, "정말 맛있습니다.","");
        spicyChicken = new SpicyChicken(spicyChickenId, "양념황금올리브", 18000, Category.CHICKEN,  "정말 맛있습니다22.", "");
        var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-jdbc-foods", classPathScript("foodSchema.sql"))
                .start();

    }

    @AfterEach
    void cleanup() {
        embeddedMysql.stop();
    }


    @Test
    @DisplayName("음식테이블 insert 테스트")
    void testInsert() {

        foodRepository.insert(friedChicken);
        foodRepository.insert(spicyChicken);

        List<Food> foods = foodRepository.findAll();
        assertThat(foods.isEmpty(), is(false));
        assertThat(foods, hasSize(2));

        assertThrows(DataAccessException.class, () -> foodRepository.insert(friedChicken));
        assertThat(foods, hasSize(2));
    }

    @Test
    @DisplayName("foods 테이블 전체 조회 테스트")
    void testFindAll() {
        List<Food> foods = foodRepository.findAll();
        assertThat(foods.isEmpty(), is(true));

        foodRepository.insert(friedChicken);
        foods = foodRepository.findAll();
        assertThat(foods.isEmpty(), is(false));
        assertThat(foods, hasSize(1));
    }

    @Test
    @DisplayName("Id 를 이용해 조회 테스트")
    void testFindById() {
        UUID chickenId = friedChickenId;
        Optional<Food> food = foodRepository.findById(chickenId);
        assertThat(food.isEmpty(), is(true));

        foodRepository.insert(friedChicken);
        food = foodRepository.findById(chickenId);
        assertThat(food.get(), samePropertyValuesAs(friedChicken));
    }

    //ToDO - 새로운 category food 추가시 다시 테스트
    @Test
    @DisplayName("category 별 조회 테스트")
    void testFindByCategory() {

        foodRepository.insert(friedChicken);
        List<Food> foods = foodRepository.findByCategory(Category.CHICKEN);

        assertThat(foods.isEmpty(), is(false));
        assertThat(foods.get(0), samePropertyValuesAs(friedChicken));
    }

    @Test
    @DisplayName("음식 정보 수정 테스트")
    void testUpdate() {
        foodRepository.insert(friedChicken);
        friedChicken.changeInfo("황올", 18000, "우리 가게 대표 메뉴");
        foodRepository.update(friedChicken);

        List<Food> foods = foodRepository.findAll();
        assertThat(foods.isEmpty(), is(false));
        assertThat(foods, hasSize(1));

        Optional<Food> food = foodRepository.findById(friedChickenId);
        assertThat(food.isEmpty(), is(false));
        assertThat(food.get(), samePropertyValuesAs(friedChicken));
    }

    @Test
    @DisplayName("특정 음식 삭제 테스트")
    void testDelete() {
        foodRepository.insert(friedChicken);
        foodRepository.insert(spicyChicken);
        List<Food> foods = foodRepository.findAll();
        assertThat(foods, hasSize(2));

        foodRepository.deleteById(friedChicken);
        foods = foodRepository.findAll();
        assertThat(foods, hasSize(1));
        assertThat(foods.get(0), samePropertyValuesAs(spicyChicken));
    }
}