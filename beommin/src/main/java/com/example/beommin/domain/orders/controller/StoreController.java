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
    public ResponseEntity<List<StoreDto>> getAllStores() {
        return ResponseEntity.ok()
                .body(storeService.getAllStores());
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<StoreDto> getStoreById(@PathVariable UUID storeId) {
        return ResponseEntity.ok()
                .body(storeService.getStoreById(storeId));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<StoreDto>> getStoreByCategory(@PathVariable String category) {
       return ResponseEntity.ok()
               .body(storeService.getStoreByCategory(category));
    }


    @PostMapping("")
    public ResponseEntity<StoreDto> insertStore(@RequestBody StoreDto storeDto) {
        return ResponseEntity.created(URI.create("/stores"))
                .body(storeService.createStore(storeDto));
    }

    @PutMapping("")
    public ResponseEntity<StoreDto> updateStore(@RequestBody StoreDto storeDto) {
        return ResponseEntity.ok(storeService.updateStore(storeDto));
    }

    @DeleteMapping("/{storeId}")
    public ResponseEntity deleteStore(@PathVariable UUID storeId) {
        System.out.println(storeId);
        storeService.deleteStore(storeId);
        return ResponseEntity.noContent().build();
    }


}
