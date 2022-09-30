package com.example.beommin.domain.menu.repository;

import com.example.beommin.domain.menu.entity.Menu;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {


  Slice<Menu> findByStoreIdOrderByOrderCount(Long storeId, Pageable pageable);
}
