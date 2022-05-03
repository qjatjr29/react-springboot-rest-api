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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StoreService {
    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

//    @Override
    public List<Store> getStoreByCategory(StoreCategory category) {
        return storeRepository.findByCategory(category);
    }

//    @Override
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    //TODO  restapi 만들면 해야함
//    @Override
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

//    @Override
    public Store getStoreById(UUID storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException());
    }

    //TODO  restapi 만들면 해야함
//    @Override
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

//    @Override
    public void deleteStore(UUID storeId) {
        Optional<Store> store = storeRepository.findById(storeId);
        store.ifPresent(storeRepository::deleteById);
    }


}
