package com.example.beommin.common.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@Access(value = AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

  @NotEmpty()
  private String addressName;

  @NotEmpty()
  private String roadAddressName;

  public Address(String addressName, String roadAddressName) {
    this.addressName = addressName;
    this.roadAddressName = roadAddressName;
  }
}
