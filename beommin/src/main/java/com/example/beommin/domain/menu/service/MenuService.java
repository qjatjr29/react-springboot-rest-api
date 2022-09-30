package com.example.beommin.domain.menu.service;

import com.example.beommin.domain.menu.dto.MenuDetailResponse;
import com.example.beommin.domain.menu.dto.MenuRequest;
import com.example.beommin.domain.menu.dto.MenuSummaryResponse;
import com.example.beommin.domain.menu.dto.UpdateMenuRequest;
import com.example.beommin.domain.menu.entity.Menu;
import com.example.beommin.domain.menu.repository.MenuRepository;
import com.example.beommin.domain.menu.util.MenuConverter;
import com.example.beommin.domain.store.entity.Store;
import com.example.beommin.domain.store.entity.StoreCategory;
import com.example.beommin.domain.store.repository.StoreCategoryRepository;
import com.example.beommin.domain.store.repository.StoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MenuService {

  private final MenuRepository menuRepository;
  private final StoreRepository storeRepository;
  private final StoreCategoryRepository storeCategoryRepository;

  public MenuService(MenuRepository menuRepository,
      StoreRepository storeRepository,
      StoreCategoryRepository storeCategoryRepository) {
    this.menuRepository = menuRepository;
    this.storeRepository = storeRepository;
    this.storeCategoryRepository = storeCategoryRepository;
  }

  @Transactional
  public Long register(Long storeId, MenuRequest menuRequest) {

    Store store = storeRepository.findById(storeId)
        .orElseThrow(IllegalArgumentException::new);

    StoreCategory storeCategory = storeCategoryRepository.findByCategoryAndStore(
            menuRequest.getCategory(), store)
        .orElse(StoreCategory.builder()
            .category(menuRequest.getCategory())
            .count(1l)
            .store(store)
            .build());

    Menu register = MenuConverter.toMenu(store, menuRequest);
    Menu menu = menuRepository.save(register);

    store.addCategory(storeCategory);

    return menu.getId();
  }

  public MenuDetailResponse findDetail(Long menuId) {

    Menu menu = menuRepository.findById(menuId)
        .orElseThrow(IllegalArgumentException::new);

    return MenuConverter.toDetail(menu);
  }

  public Page<MenuSummaryResponse> findMenuList(Pageable pageable) {

    Page<Menu> menus = menuRepository.findAll(pageable);
    return menus.map(MenuConverter::toMenuList);
  }


  public Slice<MenuSummaryResponse> findStoreMenu(Long storeId, Pageable pageable) {

    Slice<Menu> menus = menuRepository.findByStoreIdOrderByOrderCount(storeId,
        pageable);

    return menus.map(MenuConverter::toMenuList);
  }

  @Transactional
  public MenuDetailResponse modify(Long menuId, UpdateMenuRequest updateRequest) {
    Menu menu = menuRepository.findById(menuId)
        .orElseThrow(IllegalArgumentException::new);

    Store store = menu.getStore();

    menu.changeName(updateRequest.getName());
    menu.changePrice(updateRequest.getPrice());
    menu.changeDescription(updateRequest.getDescription());
    menu.changeState(updateRequest.getState());

    if(!menu.getCategory().equals(updateRequest.getCategory())) {
      StoreCategory preCategory = storeCategoryRepository.findByCategoryAndStore(
          menu.getCategory(), store).get();
      StoreCategory storeCategory = storeCategoryRepository.findByCategoryAndStore(
              updateRequest.getCategory(), store)
          .orElse(StoreCategory.builder()
              .category(updateRequest.getCategory())
              .count(1l)
              .store(store)
              .build());
      store.changeCategory(preCategory, storeCategory);
      preCategory.changeCategory(updateRequest.getCategory());
      menu.changeCategory(updateRequest.getCategory());

      storeCategoryRepository.save(preCategory);
    }

    menuRepository.save(menu);

    return MenuConverter.toDetail(menu);
  }

  @Transactional
  public void delete(Long menuId) {
    Menu menu = menuRepository.findById(menuId)
        .orElseThrow(IllegalArgumentException::new);

    Store store = menu.getStore();
    store.deleteCategory(menu.getCategory());

    menuRepository.deleteById(menuId);
  }
}
