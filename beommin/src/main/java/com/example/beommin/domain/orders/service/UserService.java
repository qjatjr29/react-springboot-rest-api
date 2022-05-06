package com.example.beommin.domain.orders.service;

import com.example.beommin.domain.orders.controller.user.UserDto;
import com.example.beommin.domain.orders.entity.User;
import com.example.beommin.domain.orders.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.beommin.Utils.validEmail;
import static com.example.beommin.Utils.validPassword;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        users.forEach(user -> userDtoList.add(UserDto.of(user)));
        return userDtoList;
    }

    public Optional<UserDto> createUser(UserDto userDto) {

        if(validateEmailAndPassword(userDto.getEmail(), userDto.getPassword())) return Optional.empty();

        Optional<User> isExistUser = userRepository.findByEmail(userDto.getEmail());
        if(isExistUser.isPresent()) {
            logger.info("유저 생성 실패!");
            return Optional.empty();
        }
        User user = new User(
                    UUID.randomUUID(),
                    userDto.getEmail(),
                    userDto.getPassword(),
                    userDto.getName(),
                    userDto.getAddress(),
                    userDto.getPhoneNumber(),
                    LocalDate.now()
            );
        userRepository.insert(user);
        logger.info("유저 생성!");
        return Optional.of(UserDto.of(user));

    }

    public UserDto getUserById(UUID userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        return getUserDto(user);
    }

    public UUID login(String loginId, String password) {
        Optional<User> login = userRepository.login(loginId, password);
        return getUserDto(login).getUserId();
    }

    public UserDto updateUser(UserDto userDto) {
        Optional<User> user = userRepository.findByUserId(userDto.getUserId());
        User updateUser = Optional.of(user).get()
                .orElseThrow(RuntimeException::new);
        updateUser.changeInfo(
                userDto.getName(),
                userDto.getAddress(),
                userDto.getPhoneNumber()
        );
        userRepository.update(updateUser);
        return userDto;
    }

    public void deleteUser(UUID userId) {
        User user = Optional.of(userRepository.findByUserId(userId)).get()
                .orElseThrow(RuntimeException::new);
        userRepository.deleteById(user);
    }


    private UserDto getUserDto(Optional<User> user) {
        User getUser = Optional.of(user).get()
                .orElseThrow(RuntimeException::new);
        return UserDto.of(getUser);
    }

    private boolean validateEmailAndPassword(String email, String password) {
        if(!validEmail(email) || !validPassword(password)) return false;
        return true;
    }

}
