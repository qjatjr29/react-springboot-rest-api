package com.example.beommin.domain.store.repository;

import com.example.beommin.domain.menu.entity.Category;
import com.example.beommin.domain.store.entity.Store;
import com.example.beommin.domain.store.entity.StoreCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreCategoryRepository extends JpaRepository<StoreCategory, Long> {

  Optional<StoreCategory> findByCategoryAndStore(Category category, Store store);
}
