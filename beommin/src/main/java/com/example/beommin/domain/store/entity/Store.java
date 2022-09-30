package com.example.beommin.domain.store.entity;

import static java.util.Objects.isNull;
import static org.apache.logging.log4j.util.Strings.isBlank;

import com.example.beommin.common.entity.Address;
import com.example.beommin.common.entity.BaseEntity;
import com.example.beommin.common.entity.PhoneNumber;
import com.example.beommin.domain.like.entity.StoreLike;
import com.example.beommin.domain.menu.entity.Category;
import com.example.beommin.domain.orders.entity.Order;
import com.example.beommin.domain.review.entity.Review;
import com.example.beommin.domain.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
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
@Table(name = "store")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE store SET is_deleted = true WHERE id = ?")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Store extends BaseEntity {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "address")
  private Address address;

  @Column(name = "phone_number")
  private PhoneNumber phoneNumber;

  @Column(name = "description")
  @Builder.Default
  private String description = "";

  @Enumerated(EnumType.STRING)
  @Column(name = "state")
  private StoreState state;

  @Column(name = "operation_hours")
  private String operationHours;

  @Column(name = "holiday")
  private String holiday;

  @Column(name = "min_order_cost")
  @Builder.Default
  private Long minOrderCost = 0l;

  @Column(name = "delivery_tip")
  @Builder.Default
  private Long deliveryTip = 0l;

  @Column(name = "delivery_time")
  @Builder.Default
  private Long deliveryTime = 0l;

  @Column(name = "rating")
  @Builder.Default
  private Float rating = 0.0f;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

//  @Enumerated(EnumType.STRING)
//  @ElementCollection(fetch = FetchType.LAZY)
  @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
  @Column(name = "category_list")
  private List<StoreCategory> categoryList;

  @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<Order> orders = new ArrayList<>();

  @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<Review> reviews = new ArrayList<>();

  @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<StoreLike> likes = new ArrayList<>();

  @Column(name = "is_deleted")
  @Builder.Default
  private Boolean isDeleted = Boolean.FALSE;

  public void changeName(String name) {
    if(validBlank(name)) setName(name);
  }

  public void changeDescription(String description) {
    if(validBlank(description)) setDescription(description);
  }

  public void changeOperationHours(String operationHours) {
    if(validBlank(operationHours)) setOperationHours(operationHours);
  }

  public void changeHoliday(String holiday) {
    if(validBlank(holiday)) setHoliday(holiday);
  }

  public void changeMinOrderCost(Long minOrderCost) {
    if(validNull(minOrderCost)) setMinOrderCost(minOrderCost);
  }

  public void changeDeliveryTip(Long deliveryTip) {
    if(validNull(deliveryTip)) setDeliveryTip(deliveryTip);
  }

  public void changeDeliveryTime(Long deliveryTime) {
    if(validNull(deliveryTime)) setDeliveryTime(deliveryTime);
  }

  public void changeAddress(Address address) {
    if(validNull(address)) setAddress(address);
  }

  public void changePhoneNumber(PhoneNumber phoneNumber) {
    if(validNull(phoneNumber)) setPhoneNumber(phoneNumber);
  }

  public void addCategory(StoreCategory category) {
    if(validNull(category)) {
      categoryList.stream()
          .filter(cg -> cg.getCategory().equals(category.getCategory()))
          .findFirst()
          .ifPresent(cg -> {
            Long count = cg.getCount();
            cg.changeCount(count + 1);
            return;
          });
      categoryList.add(category);
    }
  }

  public void changeCategory(StoreCategory preCategory, StoreCategory postCategory) {
    if(validNull(preCategory)) {
      categoryList.stream()
          .filter(cg -> cg.getCategory().equals(preCategory.getCategory()))
          .findFirst()
          .ifPresent(cg -> {
            Long count = cg.getCount();
            if(count == 1) {
              categoryList.remove(cg);
            }
            else cg.changeCount(count - 1);
          });
    }
    addCategory(postCategory);
  }

  public void deleteCategory(Category category) {
    if(validNull(category)) {
      try {
        categoryList.stream()
            .filter(cg -> cg.getCategory().equals(category))
            .findFirst()
            .ifPresent(cg -> {
              Long count = cg.getCount();
              if(count == 1) {
                categoryList.remove(cg);
              }
              else cg.changeCount(count - 1);
            });
      } catch (Exception e) {
        throw new IllegalArgumentException();
      }
    }
  }

  public void addLike(StoreLike storeLike) {
    this.likes.add(storeLike);
  }

  public void deleteLike(StoreLike storeLike) {
    this.likes.remove(storeLike);
  }

  private boolean validNull(Object target) {
    if(isNull(target)) return false;
    return true;
  }

  private boolean validBlank(String target) {
    if(isBlank(target)) return false;
    return true;
  }

  private void setName(String name) {
    this.name = name;
  }

  private void setAddress(Address address) {
    this.address = address;
  }

  private void setPhoneNumber(PhoneNumber phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  private void setDescription(String description) {
    this.description = description;
  }

  private void setState(StoreState state) {
    this.state = state;
  }

  private void setOperationHours(String operationHours) {
    this.operationHours = operationHours;
  }

  private void setHoliday(String holiday) {
    this.holiday = holiday;
  }

  private void setMinOrderCost(Long minOrderCost) {
    this.minOrderCost = minOrderCost;
  }

  private void setDeliveryTip(Long deliveryTip) {
    this.deliveryTip = deliveryTip;
  }

  private void setDeliveryTime(Long deliveryTime) {
    this.deliveryTime = deliveryTime;
  }

}
