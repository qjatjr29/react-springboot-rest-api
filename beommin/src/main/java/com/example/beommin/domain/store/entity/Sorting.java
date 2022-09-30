package com.example.beommin.domain.store.entity;

import static com.example.beommin.domain.menu.entity.QMenu.menu;
import static com.example.beommin.domain.store.entity.QStore.store;

import com.querydsl.core.types.OrderSpecifier;

public enum Sorting {

  최신순 {
    @Override
    public OrderSpecifier<?> expression() {
      return store.createdAt.desc();
    }

    @Override
    public OrderSpecifier<?> expressionForMenu() {
      return menu.createdAt.desc();
    }
  },
  인기순 {
    @Override
    public OrderSpecifier<?> expression() {
      return store.likes.size().desc();
    }

    @Override
    public OrderSpecifier<?> expressionForMenu() {
      return menu.likes.size().desc();
    }
  };


  public abstract OrderSpecifier<?> expression();

  public abstract OrderSpecifier<?> expressionForMenu();
}
