package com.example.beommin.domain.orders.controller.user;

import com.example.beommin.domain.orders.entity.user.Person;
import com.example.beommin.domain.orders.entity.user.User;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.UUID;

import static com.example.beommin.Utils.createModelMapper;

public class PersonDto {
    private UUID userId;
    private String email;
    private String password;
    private String name;
    private String address;
    private String phoneNumber;
    private LocalDate createdAt;

    private static final ModelMapper modelMapper = createModelMapper();

    public PersonDto() {
    }

    public PersonDto(UUID userId, String email, String password, String name, String address, String phoneNumber, LocalDate createdAt) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
    }

    public static PersonDto of (Person person) {
        return modelMapper.map(person, PersonDto.class);
    }

    public UUID getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "PersonDto{" +
                "personId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
