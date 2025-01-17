package com.example.beommin.domain.user.repository;

import com.example.beommin.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  Optional<User> findByEmailAndProvider(String email, String provider);

  @Query("SELECT u.refreshToken FROM User u WHERE u.id=:id")
  String getRefreshTokenById(@Param("id") Long id);

  @Transactional
  @Modifying
  @Query("UPDATE User u SET  u.refreshToken=:token WHERE u.id=:id")
  void updateRefreshToken(@Param("id") Long id, @Param("token") String token);

}
