package com.example.beommin.domain.orders.controller.user;

import com.example.beommin.domain.orders.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/all")
    public ResponseEntity getAllUsers() {
        return ResponseEntity.ok()
                .body(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity getUserById(@PathVariable UUID userId) {
        return ResponseEntity.ok()
                .body(userService.getUserById(userId));
    }

    @GetMapping("/login")
    public ResponseEntity login(@RequestParam String email,
                                         @RequestParam String password) {
        return ResponseEntity.ok()
                .body(userService.login(email, password));
    }

    @PostMapping("")
    public ResponseEntity createUser(@RequestBody PersonDto personDto) {
        Optional<PersonDto> user = userService.createUser(personDto);
        if(user.isEmpty()) return ResponseEntity
                .created(URI.create("/users"))
                .body(personDto);
        return ResponseEntity.badRequest().body("이미 있는 아이디 입니다.");
    }

    @PutMapping("")
    public ResponseEntity updateUser(@RequestBody PersonDto personDto) {
        userService.updateUser(personDto);
        return ResponseEntity.ok()
                .body(personDto);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}
