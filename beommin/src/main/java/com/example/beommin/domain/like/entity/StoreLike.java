package com.example.beommin.domain.like.entity;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.example.beommin.common.exception.BadRequestException;
import com.example.beommin.domain.store.entity.Store;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "store_like")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreLike extends Like {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "store_id")
  private Store store;

  public StoreLike(Long userId, Store store) {
    super(userId);
    setStore(store);
  }

  public void setStore(Store store) {

    if(isNull(store)) throw new BadRequestException("가게 정보가 잘못 들어왔습니다.");

    if(nonNull(this.store)) this.store.getLikes().remove(this);

    this.store = store;
    store.addLike(this);
  }

  public void deleteStore(Store store) {
    store.deleteLike(this);
    this.store = store;
  }


}
