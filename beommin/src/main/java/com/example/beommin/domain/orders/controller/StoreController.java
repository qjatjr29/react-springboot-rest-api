package com.example.beommin.domain.orders.controller;

import com.example.beommin.domain.orders.entity.food.Food;
import com.example.beommin.domain.orders.entity.store.Store;
import com.example.beommin.domain.orders.service.FoodService;
import com.example.beommin.domain.orders.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/all")
    public ResponseEntity getAllStores() {
        List<Store> stores = storeService.getAllStores();
        List<StoreDto> foodDtos = new ArrayList<>();
        stores.forEach(food -> foodDtos.add(food.toDto()));
        return ResponseEntity.ok()
                .body(foodDtos);
    }

    @GetMapping("/{storeId}")
    public ResponseEntity getStoreById(@PathVariable UUID storeId) {
        Store store = storeService.getStoreById(storeId);
        return ResponseEntity.ok()
                .body(store.toDto());
    }


    @PostMapping("")
    public ResponseEntity insertStore(@RequestBody StoreDto storeDto) {
        Store store = storeService.createStore(storeDto);
        return ResponseEntity.created(URI.create("/stores"))
                .body(store.toDto());
    }

    @PutMapping("")
    public ResponseEntity updateStore(@RequestBody StoreDto storeDto) {
        Store store = storeService.updateStore(storeDto);
        return ResponseEntity.ok(store.toDto());
    }

    @DeleteMapping("/{storeId}")
    public ResponseEntity deleteStore(@PathVariable UUID storeId) {
        storeService.deleteStore(storeId);
        return ResponseEntity.noContent().build();
    }


}
