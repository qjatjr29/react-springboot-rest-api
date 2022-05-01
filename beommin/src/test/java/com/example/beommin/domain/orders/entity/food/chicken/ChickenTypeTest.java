package com.example.beommin.domain.orders.entity.food.chicken;

import com.example.beommin.domain.orders.entity.food.Category;
import com.example.beommin.domain.orders.entity.food.Food;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class ChickenTypeTest {

    ChickenType chickenType;

    @Test
    @DisplayName("입력된 치킨 종류에 따라 각각의 종류의 치킨이 리턴되는지 테스트")
    void testGetChickenType() {
        chickenType = ChickenType.getChickenType("후라이드");
        assertThat(chickenType.getType().equals("후라이드"), is(true));
        chickenType = ChickenType.getChickenType("양념");
        assertThat(chickenType.getType().equals("반반"), is(false));
    }

    @Test
    void testCreate() {
        chickenType = ChickenType.getChickenType("후라이드");
        Food chicken = chickenType.createFood("황금올리브", 17000, Category.CHICKEN, "정말 맛있어요!", "");
        assertThat(chicken.getClass().equals(FriedChicken.class), is(true));

        chickenType = ChickenType.getChickenType("양념");
        chicken = chickenType.createFood("황금올리브양념", 18000, Category.CHICKEN, "정말 맛있어요22!", "");
        assertThat(chicken.getClass().equals(SpicyChicken.class), is(true));

    }
}