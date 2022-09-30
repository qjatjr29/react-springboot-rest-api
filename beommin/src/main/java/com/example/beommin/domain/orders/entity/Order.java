package com.example.beommin.domain.orders.entity;

import static java.util.Objects.isNull;

import com.example.beommin.common.entity.Address;
import com.example.beommin.common.entity.BaseEntity;
import com.example.beommin.common.entity.PhoneNumber;
import com.example.beommin.domain.store.entity.Store;
import java.util.ArrayList;
import java.util.List;
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
@Table(name = "orders")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE orders SET is_deleted = true WHERE id = ?")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Order extends BaseEntity {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @Column(name = "user_id")
  private Long user;

  @Column(name = "address")
  private Address address;

  @Column(name = "phnoe_number")
  private PhoneNumber phoneNumber;

  @Column(name = "cost")
  private Long cost;

  @Enumerated(EnumType.STRING)
  @Column(name = "order_type")
  private OrderType orderType;

  @Enumerated(EnumType.STRING)
  @Column(name = "order_state")
  private OrderState orderState;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "store_id")
  private Store store;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<OrderItem> orderItem = new ArrayList<>();

  @Column(name = "is_deleted")
  @Builder.Default
  private Boolean isDeleted = Boolean.FALSE;

  public void changeAddress(Address address) {
    if(validateNull(address)) setAddress(address);
  }

  public void changePhoneNumber(PhoneNumber phoneNumber) {
    if(validateNull(phoneNumber)) setPhoneNumber(phoneNumber);
  }

  public void changeCost(Long cost) {
    if(validateNull(cost)) setCost(cost);
  }

  public void changeOrderType(OrderType orderType) {
    if(validateNull(orderType)) setOrderType(orderType);
  }

  public void changeOrderState(OrderState orderState) {
    if(validateNull(orderState)) setOrderState(orderState);
  }

  private boolean validateNull(Object target) {
    if(isNull(target)) return false;
    return true;
  }

  private void setAddress(Address address) {
    this.address = address;
  }

  private void setPhoneNumber(PhoneNumber phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  private void setCost(Long cost) {
    this.cost = cost;
  }

  private void setOrderType(OrderType orderType) {
    this.orderType = orderType;
  }

  private void setOrderState(OrderState orderState) {
    this.orderState = orderState;
  }
}
