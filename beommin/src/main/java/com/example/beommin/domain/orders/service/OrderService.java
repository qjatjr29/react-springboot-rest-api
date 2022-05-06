package com.example.beommin.domain.orders.service;

import com.example.beommin.domain.orders.controller.OrderDto;
import com.example.beommin.domain.orders.controller.OrderItemDto;
import com.example.beommin.domain.orders.entity.Order;
import com.example.beommin.domain.orders.entity.OrderItem;
import com.example.beommin.domain.orders.entity.OrderList;
import com.example.beommin.domain.orders.entity.OrderStatus;
import com.example.beommin.domain.orders.entity.food.Category;
import com.example.beommin.domain.orders.repository.OrderItemRepository;
import com.example.beommin.domain.orders.repository.OrderListRepository;
import com.example.beommin.domain.orders.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.beommin.Utils.phoneFormat;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderListRepository orderListRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository orderRepository, OrderListRepository orderListRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderListRepository = orderListRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public OrderDto createOrder(OrderDto orderDto) {
        UUID orderId = UUID.randomUUID();

        List<OrderItem> orderItems = getOrderItems(orderDto, orderId);

        Order order = Order.builder()
                .orderId(orderId)
                .name(orderDto.getName())
                .address(orderDto.getAddress())
                .phoneNumber(orderDto.getPhoneNumber())
                .price(orderDto.getPrice())
                .orderItems(orderItems)
                .orderStatus(OrderStatus.ACCEPTED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        orderRepository.insert(order);
        return orderDto;
    }

    public OrderDto createOrderList(OrderDto orderDto) {
        UUID orderListId = UUID.randomUUID();

        List<OrderItem> orderItemsList = getOrderItems(orderDto, orderListId);

        OrderList orderList = OrderList.builder()
                .orderListId(orderListId)
                .name(orderDto.getName())
                .address(orderDto.getAddress())
                .phoneNumber(orderDto.getPhoneNumber())
                .orderItems(orderItemsList)
                .updatedAt(LocalDateTime.now())
                .build();
        orderListRepository.insert(orderList);

        return orderDto;
    }

    // TODO - 장바구니에서 바로 주문
//    public void changeOrderListToOrder(OrderList orderList) {
//        UUID orderId = UUID.randomUUID();
//
//        OrderDto od = orderList.toDto();
//        UUID preId = od.getId();
//
//        List<OrderItem> items = orderItemRepository.findByOrderId(preId);
//        int price = 0;
//        for (OrderItem item : items) {
//            price += OrderItemDto.of(item).getPrice();
//            orderItemRepository.deleteById(item, preId);
//        }
//
//        Order order = Order.builder()
//                .orderId(orderId)
//                .name(od.getName())
//                .address(od.getAddress())
//                .phoneNumber(od.getPhoneNumber())
//                .price(price)
//                .orderStatus(OrderStatus.ACCEPTED)
//                .createdAt(LocalDateTime.now())
//                .updatedAt(LocalDateTime.now())
//                .build();
//        orderRepository.insert(order);
//
//        Optional<OrderList> deleteOrderList = orderListRepository.findByOrderListId(preId);
//        deleteOrderList.ifPresent(orderListRepository::deleteOrderList);
//    }



    public List<OrderDto> getOrders() {
        List<Order> orderList = orderRepository.findAll();

        return orderList.stream()
                .map(OrderDto::of).collect(Collectors.toList());
    }

    public List<OrderDto> getOrderLists() {
        List<OrderList> orderList = orderListRepository.findAll();
        List<OrderDto> orderDtoList = new ArrayList<>();

        for (OrderList list : orderList) {
            List<OrderItemDto> orderItemDtoList = new ArrayList<>();
            OrderDto orderDto = OrderDto.ofList(list);
            List<OrderItem> byOrderId = orderItemRepository.findByOrderId(orderDto.getId());

            for (OrderItem orderItem : byOrderId) {
                orderItemDtoList.add(OrderItemDto.of(orderItem));
            }
            orderDto.setOrderItems(orderItemDtoList);
            orderDtoList.add(orderDto);
        }

        return orderDtoList;
    }

    public OrderDto getOrder(UUID orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        Order getOrder = Optional.of(order).get()
                .orElseThrow(RuntimeException::new);
        return OrderDto.of(getOrder);
    }

    public OrderDto getOrderList(UUID orderId) {
        Optional<OrderList> order = orderListRepository.findByOrderListId(orderId);
        OrderList orderList = Optional.of(order).get()
                .orElseThrow(RuntimeException::new);
        return OrderDto.ofList(orderList);
    }

    public List<OrderItemDto> getOrderItemsList(UUID orderId) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        orderItems.forEach(o -> orderItemDtoList.add(OrderItemDto.of(o)));
        return orderItemDtoList;
    }


    public void deleteOrder(UUID deleteId) {
        Optional<Order> deleteOrder = orderRepository.findById(deleteId);
        Order delete = Optional.of(deleteOrder).get()
                .orElseThrow(RuntimeException::new);
        orderRepository.deleteById(delete);
    }

    public void deleteOrderList(UUID deleteId) {
        Optional<OrderList> deleteOrder = orderListRepository.findByOrderListId(deleteId);
        OrderList delete = Optional.of(deleteOrder).get()
                .orElseThrow(RuntimeException::new);
        orderListRepository.deleteOrderList(delete);
    }

    private List<OrderItem> getOrderItems(OrderDto orderDto, UUID orderId) {
        List<OrderItemDto> orderItems = orderDto.getOrderItems();
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItems.forEach(it -> {
            OrderItem item = new OrderItem( it.getFoodId(),
                    Category.getCategoryType(it.getCategory()),
                    it.getPrice(),
                    it.getQuantity()
            );
            orderItemList.add(item);
            orderItemRepository.insert(item, orderId);
        });
        return orderItemList;
    }

    private void deleteOrderItem(UUID orderId) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        for (OrderItem orderItem : orderItems) {
            orderItemRepository.deleteById(orderItem, orderId);
        }
    }

}
