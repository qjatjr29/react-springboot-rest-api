package com.example.beommin.domain.orders;

import com.example.beommin.domain.orders.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class ConvertTest {

    @Test
    void testConvertPhoneNumber() {
        User customer1 = new User(UUID.randomUUID(), "beomsic", "123", "beomseok", "dd", "010-1234-5678", LocalDate.now());
        User customer2 = new User(UUID.randomUUID(), "beomsic", "123", "beomseok", "dd", "01012345678", LocalDate.now());
        User customer3 = new User(UUID.randomUUID(), "beomsic", "123", "beomseok", "dd", "010 1234  5678", LocalDate.now());
        assertThat(customer1.checkPhoneNumber("010-4874-0524"), is(true));
        assertThat(customer2.checkPhoneNumber("010-4874-0524"), is(true));
        assertThat(customer3.checkPhoneNumber("010-4874-0524"), is(true));
    }
}