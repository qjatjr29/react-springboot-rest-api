package com.example.beommin.common.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@Access(value = AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PhoneNumber {

  @Pattern(regexp = "^\\d{2,4}-\\d{3,4}-\\d{4}$")
  private String number;

  public PhoneNumber(String number) {
    this.number = number;
  }

  public static PhoneNumber of(String number) {
    return new PhoneNumber(number);
  }

}
