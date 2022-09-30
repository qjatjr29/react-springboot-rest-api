package com.example.beommin.domain.store.service;

import com.example.beommin.common.exception.BadRequestException;
import com.example.beommin.common.exception.NotFoundException;
import com.example.beommin.domain.store.dto.SearchStoreRequest;
import com.example.beommin.domain.store.dto.StoreDetailResponse;
import com.example.beommin.domain.store.dto.StoreRequest;
import com.example.beommin.domain.store.dto.StoreSummaryResponse;
import com.example.beommin.domain.store.dto.UpdateStoreRequest;
import com.example.beommin.domain.store.entity.Store;
import com.example.beommin.domain.store.repository.StoreRepository;
import com.example.beommin.domain.store.util.StoreConverter;

import com.example.beommin.domain.user.entity.User;
import com.example.beommin.domain.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class StoreService {

  private final StoreRepository storeRepository;
  private final UserRepository userRepository;

  public StoreService(StoreRepository storeRepository,
      UserRepository userRepository) {
    this.storeRepository = storeRepository;
    this.userRepository = userRepository;
  }

  @Transactional
  public Long register(StoreRequest storeRequest, Long userId) {

    // TODO: validation - 같은 주소 / 같은 연락처

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("유저 정보가 잘못되었습니다."));

    storeRepository.findByAddressOrPhoneNumber(storeRequest.getAddress(), storeRequest.getPhoneNumber())
        .ifPresent(s -> {
          throw new BadRequestException("이미 있는 주소 또는 핸드폰 번호입니다.");
        });

    Store store = StoreConverter.toStore(storeRequest, user);

    Store register = storeRepository.save(store);
    return register.getId();
  }

  public Page<StoreSummaryResponse> getStoreList(SearchStoreRequest searchStoreRequest, Pageable pageable) {

    Page<Store> stores = storeRepository.searchStore(searchStoreRequest, pageable);

    return stores.map(StoreConverter::toStoreList);
  }

  public StoreDetailResponse findDetail(Long storeId) {
    Store store = storeRepository.findById(storeId)
        .orElseThrow(IllegalArgumentException::new);

    return StoreConverter.toDetail(store);
  }

  @Transactional
  public StoreDetailResponse modify(Long storeId, UpdateStoreRequest updateStoreRequest) {
    Store store = storeRepository.findById(storeId)
        .orElseThrow(IllegalArgumentException::new);

    store.changeName(updateStoreRequest.getName());
    store.changeDescription(updateStoreRequest.getDescription());
    store.changeHoliday(updateStoreRequest.getHoliday());
    store.changeMinOrderCost(updateStoreRequest.getMinOrderCost());
    store.changeOperationHours(updateStoreRequest.getOperationHours());
    store.changeDeliveryTip(updateStoreRequest.getDeliveryTip());
    store.changeAddress(updateStoreRequest.getAddress());
    store.changePhoneNumber(updateStoreRequest.getPhoneNumber());
    store.changeDeliveryTime(updateStoreRequest.getDeliveryTime());

    storeRepository.save(store);

    return StoreConverter.toDetail(store);
  }

  @Transactional
  public void delete(Long storeId) {
    storeRepository.deleteById(storeId);
  }
}
