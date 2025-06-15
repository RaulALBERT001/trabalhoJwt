package com.raul.trabalhoJwt.controllers;

import com.raul.trabalhoJwt.domain.user.DTO.UpdateUserDTO;
import com.raul.trabalhoJwt.domain.user.DTO.UserResponseDTO;
import com.raul.trabalhoJwt.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable String userId,
            @RequestBody @Valid UpdateUserDTO data
    ) {
        return ResponseEntity.ok(userService.updateUser(userId, data));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}