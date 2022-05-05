package com.example.beommin.domain.orders.service;

import com.example.beommin.domain.orders.controller.user.UserDto;
import com.example.beommin.domain.orders.entity.User;
import com.example.beommin.domain.orders.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.beommin.Utils.validPassword;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserDto> createUser(UserDto userDto) {

        Optional<User> isExistUser = userRepository.findByEmail(userDto.getEmail());

        if(isExistUser.isEmpty() &&validPassword(userDto.getPassword())) {
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
            return Optional.of(userDto);
        }
        logger.info("유저 생성 실패!");
        return Optional.empty();
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
        if(user.isEmpty()) throw new RuntimeException();
        User updateUser = new User(
                userDto.getUserId(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getName(),
                userDto.getAddress(),
                userDto.getPhoneNumber(),
                userDto.getCreatedAt()
        );
        userRepository.update(updateUser);
        return userDto;
    }

    public void deleteUser(UUID userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        user.ifPresent(userRepository::deleteById);
    }


    private UserDto getUserDto(Optional<User> user) {
        if(user.isPresent()) return user.get().toDto();
        else throw new RuntimeException();
    }

}
