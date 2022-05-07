package com.example.beommin.domain.orders.service;

import com.example.beommin.domain.orders.controller.user.PersonDto;
import com.example.beommin.domain.orders.entity.user.User;
import com.example.beommin.domain.orders.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.beommin.Utils.*;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<PersonDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<PersonDto> personDtoList = new ArrayList<>();
        users.forEach(user -> personDtoList.add(PersonDto.of(user)));
        return personDtoList;
    }

    public Optional<PersonDto> createUser(PersonDto personDto) {

        if(!validateEmailAndPassword(personDto.getEmail(), personDto.getPassword())) return Optional.empty();
        Optional<User> isExistUser = userRepository.findByEmail(personDto.getEmail());
        if(isExistUser.isPresent()) {
            logger.info("유저 생성 실패!");
            return Optional.empty();
        }
        User user = new User(
                    UUID.randomUUID(),
                    personDto.getEmail(),
                    personDto.getPassword(),
                    personDto.getName(),
                    personDto.getAddress(),
                    personDto.getPhoneNumber(),
                    LocalDate.now()
            );
        userRepository.insert(user);
        logger.info("유저 생성!");
        return Optional.of(PersonDto.of(user));

    }

    public PersonDto getUserById(UUID userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        return getUserDto(user);
    }

    public UUID login(String loginId, String password) {
        Optional<User> login = userRepository.login(loginId, password);
        return getUserDto(login).getUserId();
    }

    public PersonDto updateUser(PersonDto personDto) {
        Optional<User> user = userRepository.findByUserId(personDto.getUserId());
        User updateUser = Optional.of(user).get()
                .orElseThrow(RuntimeException::new);
        updateUser.changeInfo(
                personDto.getName(),
                personDto.getAddress(),
                personDto.getPhoneNumber()
        );
        userRepository.update(updateUser);
        return personDto;
    }

    public void deleteUser(UUID userId) {
        User user = Optional.of(userRepository.findByUserId(userId)).get()
                .orElseThrow(RuntimeException::new);
        userRepository.deleteById(user);
    }


    private PersonDto getUserDto(Optional<User> user) {
        User getUser = Optional.of(user).get()
                .orElseThrow(RuntimeException::new);
        return PersonDto.of(getUser);
    }



}
