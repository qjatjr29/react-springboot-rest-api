package com.example.beommin.domain.orders.repository;

import com.example.beommin.domain.orders.entity.Order;
import com.example.beommin.domain.orders.entity.OrderItem;
import com.example.beommin.domain.orders.entity.OrderList;
import com.example.beommin.domain.orders.entity.OrderStatus;
import com.example.beommin.domain.orders.entity.food.Category;
import com.example.beommin.domain.orders.entity.food.chicken.FriedChicken;
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
import java.time.LocalDateTime;
import java.util.*;

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
@DisplayName("Order테스트!")
class OrderRepositoryTest {
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
    FoodRepository foodRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderListRepository orderListRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    DataSource dataSource;

    EmbeddedMysql embeddedMysql;

    Store store1 ,store2;
    FriedChicken chicken;
    OrderItem orderItem;
    static final UUID storeId1 = UUID.randomUUID();
    static final UUID storeId2 = UUID.randomUUID();
    static final UUID foodId = UUID.randomUUID();
    static final UUID orderId = UUID.randomUUID();

    @BeforeEach
    void setup() {
        store1 = new ChickenStore(storeId1, "범비큐", "서울시 강남구", "02-1234-5678", StoreCategory.CHICKEN,"", LocalDate.now());
        store2 = new ChickenStore(storeId2, "범촌치킨", "서울시 구로구", "02-1235-5678", StoreCategory.CHICKEN,"", LocalDate.now());
        chicken = new FriedChicken(foodId,"test", 15000, "test", "", storeId1);
        orderItem = new OrderItem(foodId, Category.CHICKEN,30000, 2);
        var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-jdbc-repository", classPathScript("testSchema.sql"))
                .start();

        storeRepository.insert(store1);
        storeRepository.insert(store2);
        foodRepository.insert(chicken);
    }

    @AfterEach
    void cleanup() {
        embeddedMysql.stop();
    }


    @Test
    @DisplayName("orderItem insert 테스트")
    void testOrderItemInsert() {
        orderItemRepository.insert(orderItem, orderId);
        List<OrderItem> orderItems = orderItemRepository.findAll();
        assertThat(orderItems.isEmpty(), is(false));
        assertThat(orderItems, hasSize(1));
        assertThat(orderItems.get(0), samePropertyValuesAs(orderItem));
    }

    @Test
    @DisplayName("Order insert 테스트")
    void testOrderListInsert() {

        List<OrderItem> items = new ArrayList<>();
        items.add(orderItem);
        Order order = new Order(UUID.randomUUID(), "beomsic", "01012345678", "서울 구로구", 30000, OrderStatus.ACCEPTED, LocalDateTime.now(), LocalDateTime.now());
        Order order2 = new Order(UUID.randomUUID(), "beomsic", "01012345678", "서울 구로구", items, 30000, OrderStatus.ACCEPTED, LocalDateTime.now(), LocalDateTime.now());
        orderRepository.insert(order);
        orderRepository.insert(order2);
        List<Order> orders = orderRepository.findAll();
        assertThat(orders.isEmpty(), is(false));
        assertThat(orders, hasSize(2));

        assertThrows(DataAccessException.class, () -> orderRepository.insert(order));
        assertThat(orders, hasSize(2));
    }

    @Test
    @DisplayName("orderItem 전체 조회 테스트")
    void testFindAll() {
        List<OrderItem> orderItems = orderItemRepository.findAll();
        assertThat(orderItems.isEmpty(), is(true));

        orderItemRepository.insert(orderItem, orderId);
        orderItems = orderItemRepository.findAll();
        assertThat(orderItems.isEmpty(), is(false));
        assertThat(orderItems, hasSize(1));
        assertThat(orderItems.get(0),samePropertyValuesAs(orderItem));
    }

    @Test
    @DisplayName("Id 를 이용해 조회 테스트")
    void testFindById() {

        List<OrderItem> orderItem1 = orderItemRepository.findByOrderId(orderId);
        assertThat(orderItem1.isEmpty(), is(true));

        orderItemRepository.insert(orderItem, orderId);
        orderItem1 = orderItemRepository.findByOrderId(orderId);
        assertThat(orderItem1.get(0), samePropertyValuesAs(orderItem));
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