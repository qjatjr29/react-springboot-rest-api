package com.example.beommin.domain.like.entity;

import static org.springframework.util.ObjectUtils.isEmpty;

import com.example.beommin.common.entity.BaseEntity;
import com.example.beommin.common.exception.BadRequestException;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Like extends BaseEntity {

  @Column(name = "user_id")
  private Long userId;

  public Like(Long userId) {
    setUserId(userId);
  }

  private void setUserId(Long userId) {

    if(isEmpty(userId) || userId <= 0) {
      throw new BadRequestException("잘못된 사용자 정보가 들어왔습니다.");
    }
    this.userId = userId;
  }
}
