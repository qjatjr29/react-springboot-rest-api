package com.example.beommin.domain.orders.repository;

import com.example.beommin.domain.orders.entity.user.Manager;
import com.example.beommin.domain.orders.entity.user.User;
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
@DisplayName("manager table CRUD 테스트!")
class ManagerRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryTest.class);

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
    ManagerRepository managerRepository;

    @Autowired
    DataSource dataSource;

    EmbeddedMysql embeddedMysql;

    Manager manager, manager2;
    static final UUID managerId1 = UUID.randomUUID();
    static final UUID managerId2 = UUID.randomUUID();

    @BeforeEach
    void setup() {
        var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-jdbc-repository", classPathScript("testSchema.sql"))
                .start();
        manager = new Manager(managerId1, "beomsic@naver.com", "password123!", "beomseok", "서울시 구로구", "010-4444-5555", LocalDate.now());
        manager2 = new Manager(managerId2, "beomseok@gmail.com", "paa2332!", "name", "서울시 서초구", "01044443333", LocalDate.now());
    }

    @AfterEach
    void cleanup() {
        embeddedMysql.stop();
    }


    @Test
    @DisplayName("insert 테스트")
    void testInsert() {

        managerRepository.insert(manager);

        List<Manager> managers = managerRepository.findAll();
        assertThat(managers.isEmpty(), is(false));
        assertThat(managers, hasSize(1));

    }

    @Test
    @DisplayName("전체 조회 테스트")
    void testFindAll() {
        List<Manager> managers = managerRepository.findAll();
        assertThat(managers.isEmpty(), is(true));

        managerRepository.insert(manager);
        managers = managerRepository.findAll();
        assertThat(managers.isEmpty(), is(false));
        assertThat(managers, hasSize(1));
        assertThat(managers.get(0),samePropertyValuesAs(manager));
    }

    @Test
    @DisplayName("Id 를 이용해 조회 테스트")
    void testFindById() {

        Optional<Manager> findManager = managerRepository.findByManagerId(managerId1);
        assertThat(findManager.isEmpty(), is(true));

        managerRepository.insert(manager);
        findManager = managerRepository.findByManagerId(managerId1);
        Manager find = Optional.of(findManager).get()
                .orElseThrow(RuntimeException::new);
        assertThat(find, samePropertyValuesAs(find));
    }

    @Test
    @DisplayName("email 를 이용해 조회 테스트")
    void testFindByEmail() {

        Optional<Manager> findManager = managerRepository.findByEmail("beomsic@naver.com");
        assertThat(findManager.isEmpty(), is(true));

        managerRepository.insert(manager);
        findManager = managerRepository.findByEmail("beomsic@naver.com");
        Manager find = Optional.of(findManager).get()
                .orElseThrow(RuntimeException::new);
        assertThat(find, samePropertyValuesAs(manager));
    }

    @Test
    @DisplayName("email 과 password 가 같은 manager 조회 테스트 (로그인)")
    void testLogin() {

        Optional<Manager> findManager = managerRepository.login("beomsic@naver.com", "password123!");
        assertThat(findManager.isEmpty(), is(true));

        managerRepository.insert(manager);
        findManager = managerRepository.login("beomsic@naver.com", "password123!");
        Manager find = Optional.of(findManager).get()
                .orElseThrow(RuntimeException::new);
        assertThat(find, samePropertyValuesAs(manager));
    }


    @Test
    @DisplayName("정보 수정 테스트")
    void testUpdate() {
        managerRepository.insert(manager);
        logger.info("before => {}",manager );
        manager.changeInfo("범석", "서울시 강남구", "01055554444");
        logger.info("update => {}",manager );
        managerRepository.update(manager);

        List<Manager> managers = managerRepository.findAll();
        assertThat(managers.isEmpty(), is(false));
        assertThat(managers, hasSize(1));

        Optional<Manager> findManager = managerRepository.findByManagerId(managerId1);
        assertThat(findManager.isEmpty(), is(false));
        assertThat(findManager.get(), samePropertyValuesAs(manager));
    }

    @Test
    @DisplayName("삭제 테스트")
    void testDelete() {
        managerRepository.insert(manager);
        managerRepository.insert(manager2);
        List<Manager> managers = managerRepository.findAll();
        assertThat(managers, hasSize(2));

        managerRepository.deleteById(manager);
        managers = managerRepository.findAll();
        assertThat(managers, hasSize(1));
        assertThat(managers.get(0), samePropertyValuesAs(manager2));
    }

}