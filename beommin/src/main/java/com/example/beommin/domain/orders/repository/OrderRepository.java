package com.example.beommin.domain.orders.repository;

import com.example.beommin.domain.orders.entity.Order;
import com.example.beommin.domain.store.entity.Store;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

  List<Order> findByUser(Long userId);

  Page<Order> findAllByUser(Long userId, Pageable pageable);

  Page<Order> findAllByStore(Store store, Pageable pageable);

}
