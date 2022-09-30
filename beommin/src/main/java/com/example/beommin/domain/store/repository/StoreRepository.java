package com.example.beommin.domain.store.repository;

import com.example.beommin.common.entity.Address;
import com.example.beommin.common.entity.PhoneNumber;
import com.example.beommin.domain.store.entity.Store;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreSearchRepository {

  Optional<Store> findByAddressOrPhoneNumber(Address address, PhoneNumber phoneNumber);

}
