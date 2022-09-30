package com.example.beommin.domain.store.controller;

import com.example.beommin.aop.annotation.CurrentUser;
import com.example.beommin.domain.menu.entity.Category;
import com.example.beommin.domain.store.dto.SearchStoreRequest;
import com.example.beommin.domain.store.dto.StoreDetailResponse;
import com.example.beommin.domain.store.dto.StoreRequest;
import com.example.beommin.domain.store.dto.StoreSummaryResponse;
import com.example.beommin.domain.store.dto.UpdateStoreRequest;
import com.example.beommin.domain.store.entity.Sorting;
import com.example.beommin.domain.store.entity.StoreCategory;
import com.example.beommin.domain.store.service.StoreService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stores")
public class StoreController {

  private final StoreService storeService;

  public StoreController(StoreService storeService) {
    this.storeService = storeService;
  }

  @PostMapping()
  public ResponseEntity<Long> registerStore(
      @CurrentUser CustomUserDetails user,
      @Valid @RequestBody StoreRequest storeRequest) {

    Long id = storeService.register(storeRequest, user.getId());

    return ResponseEntity.created(URI.create("/" + id)).body(id);
  }

  @GetMapping()
  public ResponseEntity<Page<StoreSummaryResponse>> getStoreList(
      @RequestParam(required = false) String keyword,
      @RequestParam(required = false) String region,
      @RequestParam(required = false) List<Category> categoryList,
      @RequestParam(required = false) Sorting sorting,
      @PageableDefault(page = 0, size = 20) Pageable pageable) {

    SearchStoreRequest searchStoreRequest = SearchStoreRequest.builder()
        .keyword(keyword)
        .categoryList(categoryList)
        .region(region)
        .sorting(sorting)
        .build();

    Page<StoreSummaryResponse> storeList = storeService.getStoreList(searchStoreRequest, pageable);
    return ResponseEntity.ok(storeList);
  }

  @GetMapping("/{storeId}")
  public ResponseEntity<StoreDetailResponse> getStoreDetail(@PathVariable Long storeId) {
    StoreDetailResponse storeDetail = storeService.findDetail(storeId);
    return ResponseEntity.ok(storeDetail);
  }

  @PutMapping("/{storeId}")
  public ResponseEntity<StoreDetailResponse> modify(@PathVariable Long storeId,@Valid @RequestBody UpdateStoreRequest updateStoreRequest) {
    StoreDetailResponse storeDetail = storeService.modify(storeId, updateStoreRequest);
    return ResponseEntity.ok(storeDetail);
  }

  @DeleteMapping("/{storeId}")
  public ResponseEntity<Void> delete(@PathVariable Long storeId) {
    storeService.delete(storeId);
    return ResponseEntity.noContent().build();
  }
}
