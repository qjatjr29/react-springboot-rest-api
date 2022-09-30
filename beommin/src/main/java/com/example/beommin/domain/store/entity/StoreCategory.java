package com.example.beommin.domain.store.entity;

import com.example.beommin.common.entity.BaseEntity;
import com.example.beommin.domain.menu.entity.Category;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "store_category")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoreCategory extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "category")
  private Category category;

  @Column(name = "count")
  private Long count;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "store")
  private Store store;

  public void changeCount(Long count) {
    setCount(count);
  }

  public void changeCategory(Category category) {
    setCategory(category);
  }

  private void setCategory(Category category) {
    this.category = category;
  }

  private void setCount(Long count) {
    this.count = count;
  }

}
