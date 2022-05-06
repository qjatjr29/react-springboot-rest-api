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
        List<Store> stores = storeRepository
                .findByCategory(StoreCategory.getCategoryType(category));

        List<StoreDto> storeDtoList = new ArrayList<>();
        stores.forEach(s -> storeDtoList.add(StoreDto.of(s)));
        return storeDtoList;
    }

    public List<StoreDto> getAllStores() {
        List<StoreDto> storeDtoList = new ArrayList<>();
        storeRepository.findAll()
                .forEach(store -> storeDtoList.add(StoreDto.of(store)));
        return storeDtoList;
    }

    public StoreDto createStore(StoreDto storeDto) {
        StoreCategory category = StoreCategory.getCategoryType(storeDto.getCategory());
        Store store = category.createStore(
                storeDto.getStoreId(),
                storeDto.getName(),
                storeDto.getAddress(),
                storeDto.getPhoneNumber(),
                category,
                storeDto.getImage(),
                storeDto.getCreatedAt());
        storeRepository.insert(store);
        return storeDto;
    }

    public StoreDto getStoreById(UUID storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(RuntimeException::new);
        return StoreDto.of(store);
    }

    public StoreDto updateStore(StoreDto storeDto) {
        Optional<Store> store = storeRepository.findById(storeDto.getStoreId());
        Store updateStore = Optional.of(store).get()
                .orElseThrow(RuntimeException::new);
        updateStore.changeInfo(
                storeDto.getName(),
                storeDto.getAddress(),
                storeDto.getPhoneNumber()
        );
        storeRepository.update(updateStore);
        return storeDto;
    }

    public void deleteStore(UUID storeId) {
        Store store = Optional.of(storeRepository.findById(storeId)).get()
                        .orElseThrow(()->new RuntimeException());
        storeRepository.deleteById(store);
    }


}
