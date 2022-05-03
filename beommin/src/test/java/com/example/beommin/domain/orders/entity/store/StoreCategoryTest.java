package com.example.beommin.domain.orders.entity.store;

import com.example.beommin.domain.orders.entity.food.Category;
import com.example.beommin.domain.orders.entity.food.Food;
import com.example.beommin.domain.orders.entity.food.chicken.ChickenType;
import com.example.beommin.domain.orders.entity.food.chicken.FriedChicken;
import com.example.beommin.domain.orders.entity.food.chicken.SpicyChicken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class StoreCategoryTest {
    StoreCategory storeCategory;

    @Test
    @DisplayName("입력된 가게 종류에 따라 제대로 종류가 리턴되는지 테스트")
    void testGetChickenType() {
        storeCategory = StoreCategory.getCategoryType("치킨");
        assertThat(storeCategory.getType().equals("치킨"), is(true));
        assertThat(storeCategory.getType().equals("피자"), is(false));
    }

    @Test
    void testCreate() {
        storeCategory = StoreCategory.getCategoryType("치킨");
        Store store = storeCategory.createStore(null, "범비큐", "서울시 강남구", "010 9876-0524", StoreCategory.CHICKEN, "", LocalDate.now());
        assertThat(store.getClass().equals(ChickenStore.class), is(true));
    }

}