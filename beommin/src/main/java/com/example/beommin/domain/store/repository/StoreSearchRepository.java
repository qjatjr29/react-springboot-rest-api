package com.example.beommin.domain.store.repository;

import com.example.beommin.domain.store.dto.SearchStoreRequest;
import com.example.beommin.domain.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface StoreSearchRepository {

  Page<Store> searchStore(SearchStoreRequest searchStoreRequest, Pageable pageable);

}
