package com.example.beommin.domain.orders.repository;

import com.example.beommin.domain.orders.entity.food.Category;
import com.example.beommin.domain.orders.entity.food.Food;
import com.example.beommin.domain.orders.entity.food.chicken.FriedChicken;
import com.example.beommin.domain.orders.entity.food.chicken.SpicyChicken;
import com.example.beommin.domain.orders.entity.store.ChickenStore;
import com.example.beommin.domain.orders.entity.store.Store;
import com.example.beommin.domain.orders.entity.store.StoreCategory;
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
import java.time.LocalDate;
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
@DisplayName("store table CRUD 테스트!")
class StoreRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(StoreRepositoryTest.class);

    @Configuration
    @ComponentScan(basePackages = {"com.example.beommin.domain.orders.repository"})
    static class Config {
        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-jdbc-repository")
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
    StoreRepository storeRepository;

    @Autowired
    DataSource dataSource;

    EmbeddedMysql embeddedMysql;

    Store store1 ,store2;
    static final UUID storeId1 = UUID.randomUUID();
    static final UUID storeId2 = UUID.randomUUID();

    @BeforeEach
    void setup() {
        store1 = new ChickenStore(storeId1, "범비큐", "서울시 강남구", "02-1234-5678", StoreCategory.CHICKEN,"", LocalDate.now());
        store2 = new ChickenStore(storeId2, "범촌치킨", "서울시 구로구", "02-1235-5678", StoreCategory.CHICKEN,"", LocalDate.now());
        var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-jdbc-repository", classPathScript("testSchema.sql"))
                .start();

    }

    @AfterEach
    void cleanup() {
        embeddedMysql.stop();
    }


    @Test
    @DisplayName("insert 테스트")
    void testInsert() {


        storeRepository.insert(store1);
        storeRepository.insert(store2);

        List<Store> stores = storeRepository.findAll();
        assertThat(stores.isEmpty(), is(false));
        assertThat(stores, hasSize(2));

        assertThrows(DataAccessException.class, () -> storeRepository.insert(store1));
        assertThat(stores, hasSize(2));
    }

    @Test
    @DisplayName("foods 테이블 전체 조회 테스트")
    void testFindAll() {
        List<Store> stores = storeRepository.findAll();
        assertThat(stores.isEmpty(), is(true));

        storeRepository.insert(store1);
        stores = storeRepository.findAll();
        assertThat(stores.isEmpty(), is(false));
        assertThat(stores, hasSize(1));
        assertThat(stores.get(0),samePropertyValuesAs(store1));
    }

    @Test
    @DisplayName("Id 를 이용해 조회 테스트")
    void testFindById() {

        Optional<Store> store = storeRepository.findById(storeId1);
        assertThat(store.isEmpty(), is(true));

        storeRepository.insert(store1);
        store = storeRepository.findById(storeId1);
        assertThat(store.get(), samePropertyValuesAs(store1));
    }

    //ToDO - 새로운 category  추가시 다시 테스트
    @Test
    @DisplayName("category 별 조회 테스트")
    void testFindByCategory() {

        storeRepository.insert(store1);
        List<Store> stores = storeRepository.findByCategory(StoreCategory.CHICKEN);

        assertThat(stores.isEmpty(), is(false));
        assertThat(stores.get(0), samePropertyValuesAs(store1));
    }

    @Test
    @DisplayName("정보 수정 테스트")
    void testUpdate() {
        storeRepository.insert(store1);
        logger.info("before => {}",store1 );
        store1.changeInfo("범범큐", "서울시 강남구", "0298765432");
        logger.info("update => {}",store1 );
        storeRepository.update(store1);

        List<Store> stores = storeRepository.findAll();
        assertThat(stores.isEmpty(), is(false));
        assertThat(stores, hasSize(1));

        Optional<Store> store = storeRepository.findById(storeId1);
        assertThat(store.isEmpty(), is(false));
        assertThat(store.get(), samePropertyValuesAs(store1));
    }

    @Test
    @DisplayName("삭제 테스트")
    void testDelete() {
        storeRepository.insert(store1);
        storeRepository.insert(store2);
        List<Store> stores = storeRepository.findAll();
        assertThat(stores, hasSize(2));

        storeRepository.deleteById(store1);
        stores = storeRepository.findAll();
        assertThat(stores, hasSize(1));
        assertThat(stores.get(0), samePropertyValuesAs(store2));
    }

}