package com.example.beommin.domain.orders.service;

import com.example.beommin.domain.orders.controller.FoodDto;
import com.example.beommin.domain.orders.controller.StoreDto;
import com.example.beommin.domain.orders.entity.food.Category;
import com.example.beommin.domain.orders.entity.food.Food;
import com.example.beommin.domain.orders.entity.store.Store;
import com.example.beommin.domain.orders.entity.store.StoreCategory;
import com.example.beommin.domain.orders.repository.FoodRepository;
import com.example.beommin.domain.orders.repository.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StoreService {
    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<StoreDto> getStoreByCategory(String category) {
        List<Store> stores = storeRepository.findByCategory(StoreCategory.getCategoryType(category));
        List<StoreDto> storeDtoList = new ArrayList<>();
        stores.forEach(s -> storeDtoList.add(s.toDto()));
        return storeDtoList;
    }

    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    public Store createStore(StoreDto storeDto) {
        StoreCategory category = StoreCategory.getCategoryType(storeDto.getCategory());
        Store store = category.createStore(
                storeDto.getStoreId(),
                storeDto.getName(),
                storeDto.getAddress(),
                storeDto.getPhoneNumber(),
                category,
                storeDto.getImage(),
                storeDto.getCreatedAt());
        return storeRepository.insert(store);
    }

    public Store getStoreById(UUID storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException());
    }

    public Store updateStore(StoreDto storeDto) {
        Optional<Store> store = storeRepository.findById(storeDto.getStoreId());
        if(store.isEmpty()) throw new RuntimeException();
        store.get().changeInfo(
                storeDto.getName(),
                storeDto.getAddress(),
                storeDto.getPhoneNumber()
        );
        return storeRepository.update(store.get());
    }

    public void deleteStore(UUID storeId) {
        Optional<Store> store = storeRepository.findById(storeId);
        store.ifPresent(storeRepository::deleteById);
    }


}
