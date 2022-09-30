package com.example.beommin.domain.orders.service;

import com.example.beommin.common.exception.NotFoundException;
import com.example.beommin.domain.menu.entity.Menu;
import com.example.beommin.domain.orders.dto.OrderDetailResponse;
import com.example.beommin.domain.orders.dto.OrderRequest;
import com.example.beommin.domain.orders.dto.OrderSummaryResponse;
import com.example.beommin.domain.orders.dto.UpdateOrderRequest;
import com.example.beommin.domain.orders.entity.Order;
import com.example.beommin.domain.orders.entity.OrderItem;
import com.example.beommin.domain.orders.entity.OrderState;
import com.example.beommin.domain.orders.repository.OrderItemRepository;
import com.example.beommin.domain.orders.repository.OrderRepository;
import com.example.beommin.domain.orders.util.OrderConverter;
import com.example.beommin.domain.store.entity.Store;
import com.example.beommin.domain.store.repository.StoreRepository;
import com.example.beommin.domain.user.entity.User;
import com.example.beommin.domain.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;
  private final UserRepository userRepository;
  private final StoreRepository storeRepository;

  @Transactional
  public Long save(Long userId, Long storeId, OrderRequest orderRequest) {

    User user = userRepository.findById(userId)
        .orElseThrow(IllegalArgumentException::new);

    Store store = storeRepository.findById(storeId)
        .orElseThrow(IllegalArgumentException::new);

    Order order = orderRepository.save(OrderConverter.toOrder(orderRequest, store, userId));

    AtomicLong cost = new AtomicLong(0l);

    IntStream.range(0, orderRequest.getMenuList().size()).forEach(index -> {
      Menu menu = orderRequest.getMenuList().get(index);
      cost.addAndGet(menu.getPrice());
      orderItemRepository.save(OrderConverter.toOrderItem(order, menu));
    });

    return order.getId();
  }

  public OrderDetailResponse findOrderDetail(Long userId, Long orderId) {

    User user = userRepository.findById(userId)
        .orElseThrow(IllegalArgumentException::new);

    Order order = orderRepository.findById(orderId)
        .orElseThrow(IllegalArgumentException::new);

    if(!order.getUser().equals(userId)) throw new IllegalArgumentException();

    List<OrderItem> orderItemList = orderItemRepository.findAllByOrder(order);

    return OrderConverter.toOrderDetail(user.getName(), order, orderItemList);
  }

  public Page<OrderSummaryResponse> findUserOrderList(Long userId, Pageable pageable) {

    User user = userRepository.findById(userId)
        .orElseThrow(IllegalArgumentException::new);

    Page<Order> orderListByUser = orderRepository.findAllByUser(userId, pageable);

    Page<OrderSummaryResponse> orderSummaryList = orderListByUser
        .map(order -> OrderConverter.toUserOrderSummary(order, user.getName())
    );

    return orderSummaryList;
  }

  public Page<OrderSummaryResponse> findStoreOrderList(Long storeId, Long userId, Pageable pageable) {

    Store store = storeRepository.findById(storeId)
        .orElseThrow(IllegalArgumentException::new);

    if(!store.getUser().getId().equals(userId)) throw new IllegalArgumentException();

    Page<Order> orderListByStore = orderRepository.findAllByStore(store, pageable);
//    List<String> userNameList = new ArrayList<>();
//
//    orderListByStore.stream()
//        .forEach(order -> {
//          String userName = userRepository.findById(order.getUser())
//              .orElseThrow(IllegalArgumentException::new)
//              .getName();
//          userNameList.add(userName);
//        });

    // todo : 유저 네임 적출 하여 response 만들기
    Page<OrderSummaryResponse> orderSummaryResponses = orderListByStore
        .map((order) -> {
          User user = userRepository.findById(order.getUser())
              .orElseThrow(() -> new NotFoundException("없는 유저"));
          return OrderConverter.toStoreOrderSummary(order, user.getName(), store.getName());
        });

    return orderSummaryResponses;
  }

  @Transactional
  public Long update(Long userId, Long orderId, UpdateOrderRequest orderRequest) {

    User user = userRepository.findById(userId)
        .orElseThrow(IllegalArgumentException::new);

    Order order = orderRepository.findById(orderId)
        .orElseThrow(IllegalArgumentException::new);

    if(!order.getUser().equals(userId)) throw new IllegalArgumentException();

    order.changeAddress(orderRequest.getAddress());
    order.changePhoneNumber(orderRequest.getPhoneNumber());
    order.changeCost(orderRequest.getCost());
    order.changeOrderType(orderRequest.getOrderType());

    orderRepository.save(order);

    return orderId;
  }

  @Transactional
  public Long updateState(Long orderId, OrderState orderState) {

    Order order = orderRepository.findById(orderId)
        .orElseThrow(IllegalArgumentException::new);

    order.changeOrderState(orderState);

    orderRepository.save(order);

    return orderId;
  }

  @Transactional
  public void delete(Long userId, Long orderId) {

    Order order = orderRepository.findById(orderId)
        .orElseThrow(IllegalArgumentException::new);

    if(!order.getUser().equals(userId)) throw new IllegalArgumentException();

    order.changeOrderState(OrderState.CANCEL_ORDER);
    orderRepository.delete(order);
  }
}
