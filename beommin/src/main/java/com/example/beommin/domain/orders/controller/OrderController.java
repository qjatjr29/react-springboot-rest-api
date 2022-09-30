package com.example.beommin.domain.orders.controller;

import com.example.beommin.aop.annotation.CurrentUser;
import com.example.beommin.domain.menu.entity.Menu;
import com.example.beommin.domain.orders.dto.OrderDetailResponse;
import com.example.beommin.domain.orders.dto.OrderRequest;
import com.example.beommin.domain.orders.dto.OrderSummaryResponse;
import com.example.beommin.domain.orders.dto.UpdateOrderRequest;
import com.example.beommin.domain.orders.dto.UpdateOrderState;
import com.example.beommin.domain.orders.service.OrderService;
import com.example.beommin.security.CustomUserDetails;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping("/{storeId}")
  public ResponseEntity<Long> createOrder(
      @CurrentUser CustomUserDetails user,
      @PathVariable Long storeId, @RequestBody OrderRequest orderRequest) {
    Long id = orderService.save(user.getId(), storeId, orderRequest);
    return ResponseEntity.created(URI.create("/" + id)).body(id);
  }

  // 사용자의 주문 내역
  @GetMapping("/user")
  public ResponseEntity<Page<OrderSummaryResponse>> getUserOrderList(
      @CurrentUser CustomUserDetails user,
      @PageableDefault(page = 0, size = 10) Pageable pageable
  ) {
    Page<OrderSummaryResponse> orderSummaryResponses = orderService.findUserOrderList(user.getId(), pageable);
    return ResponseEntity.ok(orderSummaryResponses);
  }

  // 가게의 주문내역
  @GetMapping("/store/{storeId}")
  public ResponseEntity<Page<OrderSummaryResponse>> getStoreOrderList(
      @CurrentUser CustomUserDetails user,
      @PathVariable Long storeId,
      @PageableDefault(page = 0, size = 10) Pageable pageable
  ) {
    Page<OrderSummaryResponse> orderSummaryResponses = orderService.findStoreOrderList(storeId, user.getId(), pageable);
    return ResponseEntity.ok(orderSummaryResponses);
  }


  @GetMapping("/order/{orderId}")
  public ResponseEntity<OrderDetailResponse> getOrderDetail(
      @CurrentUser CustomUserDetails user,
      @PathVariable Long orderId) {
    OrderDetailResponse orderDetail = orderService.findOrderDetail(user.getId(), orderId);
    return ResponseEntity.ok(orderDetail);
  }

  @PutMapping("/{orderId}")
  public ResponseEntity<Long> modify(
      @CurrentUser CustomUserDetails user,
      @PathVariable Long orderId,
      @RequestBody UpdateOrderRequest orderRequest) {


    orderService.update(user.getId(), orderId, orderRequest);

    return ResponseEntity.ok(orderId);
  }

  @PatchMapping("/{orderId}")
  public ResponseEntity<Long> modifyOrderState(
      @PathVariable Long orderId,
      @RequestBody UpdateOrderState orderState) {

    Long id = orderService.updateState(orderId, orderState.getOrderState());
    return ResponseEntity.ok(id);
  }

  @DeleteMapping("/{orderId}")
  public ResponseEntity<Void> delete(
      @CurrentUser CustomUserDetails user,
      @PathVariable Long orderId){

    orderService.delete(user.getId(), orderId);

    return ResponseEntity.noContent().build();
  }


}
