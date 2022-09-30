package com.example.beommin.domain.review.entity;

import static org.hibernate.internal.util.StringHelper.isBlank;

import com.example.beommin.common.entity.BaseEntity;
import com.example.beommin.domain.store.entity.Store;
import com.example.beommin.domain.user.entity.User;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "review")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE review SET is_deleted = true WHERE id = ?")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  private Long id;

  @Column(name = "user_id")
  private Long user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "store_id")
  private Store store;

  @Column(name = "content")
  private String content;

  @Column(name = "image")
  private String image;

  @Column(name = "is_deleted")
  private Boolean isDeleted;

  public void changeContent(String content) {
    if(isBlank(content)) throw new IllegalArgumentException();
    else setContent(content);
  }

  public void changeImage(String image) {
    if(isBlank(content)) throw new IllegalArgumentException();
    else setContent(content);
  }

  private void setContent(String content) {
    this.content = content;
  }

  private void setImage(String image) {
    this.image = image;
  }
}
