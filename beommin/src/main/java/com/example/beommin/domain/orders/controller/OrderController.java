package com.example.beommin.domain.orders.controller;

import com.example.beommin.domain.orders.entity.Order;
import com.example.beommin.domain.orders.entity.OrderList;
import com.example.beommin.domain.orders.entity.store.Store;
import com.example.beommin.domain.orders.service.OrderService;
import com.example.beommin.domain.orders.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


//    @PostMapping("")
//    public ResponseEntity createOrder(@RequestBody OrderDto orderDto) {
//        System.out.println("hihi");
//        System.out.println(orderDto.toString());
//        Order order = orderService.createOrder(orderDto);
//        return ResponseEntity.created(URI.create("/orders"))
//                .body(order.toMap().get("name"));
//    }
    @PostMapping("")
    public ResponseEntity createOrder(@RequestBody OrderDto orderDto) {
        orderService.createOrder(orderDto);
        return ResponseEntity.created(URI.create("/orders")).build();
    }
    @PostMapping("/list")
    public ResponseEntity createOrderList(@RequestBody OrderDto orderDto) {
        OrderDto orderList = orderService.createOrderList(orderDto);
        return ResponseEntity.created(URI.create("/orders")).build();
    }

    @GetMapping("")
    public ResponseEntity<List<OrderDto>> findOrders() {
        return ResponseEntity.ok()
                .body(orderService.getOrders());
    }

    @GetMapping("/list")
    public ResponseEntity<List<OrderDto>> findOrderLists() {
        return ResponseEntity.ok()
                .body(orderService.getOrderLists());
    }

    @GetMapping("{orderId}")
    public ResponseEntity<OrderDto> findOrder(@PathVariable UUID orderId) {
        return ResponseEntity.ok()
                .body(orderService.getOrder(orderId));
    }

    @GetMapping("/list/{orderId}")
    public ResponseEntity<OrderDto> findOrderList(@PathVariable UUID orderId) {
        return ResponseEntity.ok()
                .body(orderService.getOrderList(orderId));
    }

    @GetMapping("/orderItems/{orderId}")
    public ResponseEntity<List<OrderItemDto>> findOrderItems(@PathVariable UUID orderId) {
        return ResponseEntity.ok()
                .body(orderService.getOrderItemsList(orderId));
    }

    @DeleteMapping("{orderId}")
    public ResponseEntity deleteOrder(@PathVariable UUID orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/list/{orderListId}")
    public ResponseEntity deleteOrderList(@PathVariable UUID orderListId) {
        orderService.deleteOrderList(orderListId);
        return ResponseEntity.noContent().build();
    }


}
