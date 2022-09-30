package com.example.beommin.domain.menu.entity;

import static java.util.Objects.isNull;
import static org.apache.logging.log4j.util.Strings.isBlank;

import com.example.beommin.common.entity.BaseEntity;
import com.example.beommin.domain.like.entity.MenuLike;
import com.example.beommin.domain.orders.entity.OrderItem;
import com.example.beommin.domain.store.entity.Store;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "menu")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE menu SET is_deleted = true WHERE id = ?")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Menu extends BaseEntity {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "store_id")
  private Store store;

  @Column(name = "name")
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "category")
  private Category category;

  @Column(name = "description")
  @Builder.Default
  private String description = "";

  @Column(name = "price")
  private Long price;

  @Column(name = "order_count")
  @Builder.Default
  private Long orderCount = 0l;

  @Enumerated(EnumType.STRING)
  @Column(name = "state")
  @Builder.Default
  private MenuState state = MenuState.ON_SALE;

  @OneToMany(mappedBy = "menu")
  @Builder.Default
  private List<OrderItem> orderItem = new ArrayList<>();

  @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<MenuLike> likes = new ArrayList<>();

  @Column(name = "is_deleted")
  @Builder.Default
  private Boolean isDeleted = Boolean.FALSE;

  public void changeName(String name) {
    if(validBlank(name)) setName(name);
  }

  public void changePrice(Long price) {
    if(!isNull(price)) setPrice(price);
  }

  public void changeDescription(String description) {
    if(validBlank(description)) setDescription(description);
  }

  public void changeCategory(Category category) {
    if(!isNull(category)) setCategory(category);
  }

  public void changeState(MenuState state) {
    if(!isNull(state)) setState(state);
  }

  public void addLike(MenuLike menuLike) {
    this.likes.add(menuLike);
  }

  public void deleteLike(MenuLike menuLike) {
    this.likes.remove(menuLike);
  }

  private boolean validBlank(String target) {
    if(isBlank(target)) return false;
    return true;
  }

  private void setName(String name) {
    this.name = name;
  }

  private void setCategory(Category category) {
    this.category = category;
  }

  private void setDescription(String description) {
    this.description = description;
  }

  private void setPrice(Long price) {
    this.price = price;
  }

  private void setState(MenuState state) {
    this.state = state;
  }

}
