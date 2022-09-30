package com.example.beommin.domain.menu.controller;

import com.example.beommin.domain.menu.dto.MenuDetailResponse;
import com.example.beommin.domain.menu.dto.MenuRequest;
import com.example.beommin.domain.menu.dto.MenuSummaryResponse;
import com.example.beommin.domain.menu.dto.UpdateMenuRequest;
import com.example.beommin.domain.menu.service.MenuService;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/menus")
public class MenuController {

  private final MenuService menuService;

  public MenuController(MenuService menuService) {
    this.menuService = menuService;
  }

  @PostMapping("/{storeId}")
  public ResponseEntity<Long> registerMenu(
      @PathVariable Long storeId,
      @Valid @RequestBody MenuRequest menuRequest) {
    Long menuId = menuService.register(storeId, menuRequest);
    return ResponseEntity.created(URI.create("/" + menuId)).body(menuId);
  }

  @GetMapping()
  public ResponseEntity<Page<MenuSummaryResponse>> getMenuList(
      @PageableDefault(page = 0, size = 20) Pageable pageable) {
    Page<MenuSummaryResponse> menuList = menuService.findMenuList(pageable);
    return ResponseEntity.ok(menuList);
  }

  @GetMapping("/stores/{storeId}")
  public ResponseEntity<Slice<MenuSummaryResponse>> getStoreMenuList(@PathVariable Long storeId,
      @PageableDefault(page = 0, size = 10) Pageable pageable) {
    Slice<MenuSummaryResponse> storeMenu = menuService.findStoreMenu(storeId, pageable);
    return ResponseEntity.ok(storeMenu);
  }

  @GetMapping("/{menuId}")
  public ResponseEntity<MenuDetailResponse> getDetailMenu(@PathVariable Long menuId) {
    MenuDetailResponse menuDetail = menuService.findDetail(menuId);
    return ResponseEntity.ok(menuDetail);
  }

  @PutMapping("/{menuId}")
  public ResponseEntity<MenuDetailResponse> updateMenu(@PathVariable Long menuId, @RequestBody UpdateMenuRequest updateRequest) {

    MenuDetailResponse menuDetail = menuService.modify(menuId, updateRequest);
    return ResponseEntity.ok(menuDetail);
  }

  @DeleteMapping("/{menuId}")
  public ResponseEntity<Void> delete(@PathVariable Long menuId) {
    menuService.delete(menuId);

    return ResponseEntity.noContent().build();
  }


}
