package com.example.beommin.domain.like.repository;

import com.example.beommin.domain.like.entity.MenuLike;
import com.example.beommin.domain.menu.entity.Menu;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuLikeRepository extends JpaRepository<MenuLike, Long>{

  Optional<MenuLike> findByUserIdAndMenu(Long userId, Menu menu);
}
