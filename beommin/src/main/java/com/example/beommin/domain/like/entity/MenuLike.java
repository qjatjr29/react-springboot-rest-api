package com.example.beommin.domain.like.entity;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.example.beommin.domain.menu.entity.Menu;
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
@Table(name = "menu_like")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuLike extends Like {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "menu_id")
  private Menu menu;

  public MenuLike(Long userId, Menu menu) {
    super(userId);
    this.menu = menu;
  }

  public void setMenu(Menu menu) {

    if(isNull(menu)) throw new IllegalArgumentException();

    if(nonNull(this.menu)) this.menu.getLikes().remove(this);

    this.menu = menu;
    menu.addLike(this);
  }

  public void deleteMenu(Menu menu) {
    menu.deleteLike(this);
    this.menu = menu;
  }
}
