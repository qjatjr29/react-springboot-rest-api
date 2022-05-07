package com.example.beommin.domain.orders.controller.user;

import com.example.beommin.domain.orders.service.ManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/managers")
@CrossOrigin("*")
public class ManagerController {
    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }


    @GetMapping("/all")
    public ResponseEntity getAllManagers() {
        return ResponseEntity.ok()
                .body(managerService.getAllManagers());
    }

    @GetMapping("/{managerId}")
    public ResponseEntity getManagerById(@PathVariable UUID managerId) {
        return ResponseEntity.ok()
                .body(managerService.getManagerById(managerId));
    }

    @GetMapping("/login")
    public ResponseEntity login(@RequestParam String email,
                                @RequestParam String password) {
        return ResponseEntity.ok()
                .body(managerService.login(email, password));
    }

    @PostMapping("")
    public ResponseEntity createManager(@RequestBody PersonDto personDto) {
        Optional<PersonDto> manager = managerService.createManager(personDto);
        if(manager.isEmpty()) return ResponseEntity
                .created(URI.create("/managers"))
                .body(personDto);
        return ResponseEntity.badRequest().body("이미 있는 아이디 입니다.");
    }

    @PutMapping("")
    public ResponseEntity updateManager(@RequestBody PersonDto personDto) {
        managerService.updateManager(personDto);
        return ResponseEntity.ok()
                .body(personDto);
    }

    @DeleteMapping("{managerId}")
    public ResponseEntity deleteManager(@PathVariable UUID managerId) {
        managerService.deleteManager(managerId);
        return ResponseEntity.noContent().build();
    }
}
