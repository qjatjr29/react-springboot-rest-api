package com.example.beommin.domain.orders;

import com.example.beommin.domain.orders.entity.Customer;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class ConvertTest {

    @Test
    void testConvertPhoneNumber() {
        Customer customer1 = new Customer(UUID.randomUUID(), "beomsic", "123", "beomseok", "dd", "010-1234-5678");
        Customer customer2 = new Customer(UUID.randomUUID(), "beomsic", "123", "beomseok", "dd", "01012345678");
        Customer customer3 = new Customer(UUID.randomUUID(), "beomsic", "123", "beomseok", "dd", "010 1234  5678");
        assertThat(customer1.checkPhoneNumber("010-4874-0524"), is(true));
        assertThat(customer2.checkPhoneNumber("010-4874-0524"), is(true));
        assertThat(customer3.checkPhoneNumber("010-4874-0524"), is(true));
    }
}