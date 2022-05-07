package com.example.beommin;

import com.example.beommin.domain.orders.entity.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static com.example.beommin.Utils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class UtilsTest {

    @Test
    @DisplayName("user을 만들 때 전화번호가 올바른 패턴으로 전환되는지 테스트")
    void testConvertPhoneNumber() {
        User customer1 = new User(UUID.randomUUID(), "beomsic", "123", "beomseok", "dd", "010-1234-5678", LocalDate.now());
        User customer2 = new User(UUID.randomUUID(), "beomsic", "123", "beomseok", "dd", "01012345678", LocalDate.now());
        User customer3 = new User(UUID.randomUUID(), "beomsic", "123", "beomseok", "dd", "010 1234  5678", LocalDate.now());
        assertThat(customer1.checkPhoneNumber("010-4874-0524"), is(true));
        assertThat(customer2.checkPhoneNumber("010-4874-0524"), is(true));
        assertThat(customer3.checkPhoneNumber("010-4874-0524"), is(true));
    }


    @Test
    @DisplayName("올바른 패스워드 패턴 인지 테스트")
    void testCorrectPasswordPattern() {
        assertThat(validPassword("password"), is(false));
        assertThat(validPassword("password!"), is(false));
        assertThat(validPassword("password123"), is(false));
        assertThat(validPassword("test1234!"), is(true));
        assertThat(validPassword("password123!"), is(true));
        assertThat(validPassword("passssword123!"), is(false));
        assertThat(validPassword("abcdefghijklm1234432!@"), is(false));
    }


    @Test
    @DisplayName("이메일이 올바른 패턴인지 테스트")
    void testCorrectEmailPattern() {
        assertThat(validEmail("beomsic"), is(false));
        assertThat(validEmail("beomsic@gmail"), is(false));
        assertThat(validEmail("beomsic@gmailcom"), is(false));
        assertThat(validEmail("manager@gmail.com"), is(true));
        assertThat(validEmail("test.beomsic@gmail.com"), is(true));
        assertThat(validEmail("beomsic@gmail.com"), is(true));
        assertThat(validEmail("beomsic@test.co.kr"), is(true));
    }

    @Test
    @DisplayName("로그인시 이메일, 비밀번호 패턴 확인")
    void testCheckEmailAndPasswordPattern() {
        assertThat(validateEmailAndPassword("manager@gmail.com", "test1234!"), is(true));
        assertThat(validateEmailAndPassword("manager@gmail.com", "test1234"), is(false));
    }
}