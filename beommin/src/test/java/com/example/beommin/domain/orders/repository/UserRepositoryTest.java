package com.example.beommin.domain.orders.repository;

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

@SpringJUnitConfig
@ActiveProfiles("test")
@DisplayName("user table CRUD 테스트!")
class UserRepositoryTest {
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
    UserRepository userRepository;

    @Autowired
    DataSource dataSource;

    EmbeddedMysql embeddedMysql;

    User user, user2;
    static final UUID userId1 = UUID.randomUUID();
    static final UUID userId2 = UUID.randomUUID();

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
        user = new User(userId1, "beomsic@naver.com", "password123!", "beomseok", "서울시 구로구", "010-4444-5555", LocalDate.now());
        user2 = new User(userId2, "beomseok@gmail.com", "paa2332!", "name", "서울시 서초구", "01044443333", LocalDate.now());
    }

    @AfterEach
    void cleanup() {
        embeddedMysql.stop();
    }


    @Test
    @DisplayName("insert 테스트")
    void testInsert() {

        userRepository.insert(user);

        List<User> users = userRepository.findAll();
        assertThat(users.isEmpty(), is(false));
        assertThat(users, hasSize(1));

    }

    @Test
    @DisplayName("전체 조회 테스트")
    void testFindAll() {
        List<User> users = userRepository.findAll();
        assertThat(users.isEmpty(), is(true));

        userRepository.insert(user);
        users = userRepository.findAll();
        assertThat(users.isEmpty(), is(false));
        assertThat(users, hasSize(1));
        assertThat(users.get(0),samePropertyValuesAs(user));
    }

    @Test
    @DisplayName("Id 를 이용해 조회 테스트")
    void testFindById() {

        Optional<User> findUser = userRepository.findByUserId(userId1);
        assertThat(findUser.isEmpty(), is(true));

        userRepository.insert(user);
        findUser = userRepository.findByUserId(userId1);
        User find = Optional.of(findUser).get()
                .orElseThrow(RuntimeException::new);
        assertThat(find, samePropertyValuesAs(user));
    }

    @Test
    @DisplayName("email 를 이용해 조회 테스트")
    void testFindByEmail() {

        Optional<User> findUser = userRepository.findByEmail("beomsic@naver.com");
        assertThat(findUser.isEmpty(), is(true));

        userRepository.insert(user);
        findUser = userRepository.findByEmail("beomsic@naver.com");
        User find = Optional.of(findUser).get()
                        .orElseThrow(RuntimeException::new);
        assertThat(find, samePropertyValuesAs(user));
    }

    @Test
    @DisplayName("email 과 password 가 같은 user 조회 테스트 (로그인)")
    void testLogin() {

        Optional<User> findUser = userRepository.login("beomsic@naver.com", "password123!");
        assertThat(findUser.isEmpty(), is(true));

        userRepository.insert(user);
        findUser = userRepository.login("beomsic@naver.com", "password123!");
        User find = Optional.of(findUser).get()
                .orElseThrow(RuntimeException::new);
        assertThat(find, samePropertyValuesAs(user));
    }


    @Test
    @DisplayName("정보 수정 테스트")
    void testUpdate() {
        userRepository.insert(user);
        logger.info("before => {}",user );
        user.changeInfo("범석", "서울시 강남구", "01055554444");
        logger.info("update => {}",user );
        userRepository.update(user);

        List<User> users = userRepository.findAll();
        assertThat(users.isEmpty(), is(false));
        assertThat(users, hasSize(1));

        Optional<User> findUser = userRepository.findByUserId(userId1);
        assertThat(findUser.isEmpty(), is(false));
        assertThat(findUser.get(), samePropertyValuesAs(user));
    }

    @Test
    @DisplayName("삭제 테스트")
    void testDelete() {
        userRepository.insert(user);
        userRepository.insert(user2);
        List<User> users = userRepository.findAll();
        assertThat(users, hasSize(2));

        userRepository.deleteById(user);
        users = userRepository.findAll();
        assertThat(users, hasSize(1));
        assertThat(users.get(0), samePropertyValuesAs(user2));
    }


}