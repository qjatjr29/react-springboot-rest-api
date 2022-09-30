package com.example.beommin.domain.orders.util;


import com.example.beommin.domain.menu.entity.Menu;
import com.example.beommin.domain.orders.dto.OrderDetailResponse;
import com.example.beommin.domain.orders.dto.OrderItemResponse;
import com.example.beommin.domain.orders.dto.OrderRequest;
import com.example.beommin.domain.orders.dto.OrderSummaryResponse;
import com.example.beommin.domain.orders.entity.Order;
import com.example.beommin.domain.orders.entity.OrderItem;
import com.example.beommin.domain.orders.entity.OrderState;
import com.example.beommin.domain.store.entity.Store;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class OrderConverter {

  public static OrderItem toOrderItem(Order order, Menu menu) {
    return OrderItem.builder()
        .order(order)
        .menu(menu)
        .build();
  }


  public static Order toOrder(OrderRequest orderRequest, Store store, Long userId) {
    return Order.builder()
        .user(userId)
        .store(store)
        .address(orderRequest.getAddress())
        .phoneNumber(orderRequest.getPhoneNumber())
        .orderType(orderRequest.getOrderType())
        .orderState(OrderState.WAITING_ORDER)
        .cost(orderRequest.getCost())
        .build();
  }

  public static OrderDetailResponse toOrderDetail(String userName, Order order, List<OrderItem> orderItem) {

    AtomicLong cost = new AtomicLong(0l);
    List<OrderItemResponse> menuList = new ArrayList<>();
    LocalDateTime createdAt = order.getCreatedAt();
    Long deliveryTime = order.getStore().getDeliveryTime();

    int aHour = (int) (deliveryTime / 60);
    int aMinute = (int) (deliveryTime % 60);

    int hour = createdAt.getHour() + aHour;
    int minute = createdAt.getMinute() + aMinute;

    if(minute >= 60) {
      hour++;
      minute -= 60;
    }
    if(hour > 24) hour -= 24;

    LocalTime time = LocalTime.of(hour, minute);

    IntStream.range(0, orderItem.size()).forEach(index -> {
      Menu menu = orderItem.get(index).getMenu();
      cost.addAndGet(menu.getPrice());
      menuList.add(OrderItemResponse.builder()
          .menuName(menu.getName())
          .description(menu.getDescription())
          .price(menu.getPrice())
          .category(menu.getCategory())
          .build());
    });

    return OrderDetailResponse
        .builder()
        .userName(userName)
        .storeName(order.getStore().getName())
        .orderState(order.getOrderState())
        .orderType(order.getOrderType())
        .menuList(menuList)
        .cost(cost.get())
        .createdAt(order.getCreatedAt())
        .EstimatedTime(time)
        .build();
  }

  public static OrderSummaryResponse toUserOrderSummary(Order order, String userName) {

    List<OrderItemResponse> menuList = new ArrayList<>();

    List<OrderItem> orderItems = order.getOrderItem();

    for (OrderItem orderItem : orderItems) {

      Menu menu = orderItem.getMenu();

      menuList.add(OrderItemResponse.builder()
          .menuName(menu.getName())
          .description(menu.getDescription())
          .price(menu.getPrice())
          .category(menu.getCategory())
          .build());
    }

    return OrderSummaryResponse.builder()
        .cost(order.getCost())
        .storeName(order.getStore().getName())
        .userName(userName)
        .menuList(menuList)
        .createdAt(order.getCreatedAt())
        .build();
  }


  public static OrderSummaryResponse toStoreOrderSummary(Order order, String userName, String storeName) {

    List<OrderItemResponse> menuList = new ArrayList<>();

    List<OrderItem> orderItems = order.getOrderItem();

    for (OrderItem orderItem : orderItems) {

      Menu menu = orderItem.getMenu();

      menuList.add(OrderItemResponse.builder()
          .menuName(menu.getName())
          .description(menu.getDescription())
          .price(menu.getPrice())
          .category(menu.getCategory())
          .build());
    }

    return OrderSummaryResponse.builder()
        .cost(order.getCost())
        .storeName(storeName)
        .userName(userName)
        .menuList(menuList)
        .createdAt(order.getCreatedAt())
        .build();
  }
}
