package com.raul.trabalhoJwt.services;

import com.raul.trabalhoJwt.domain.user.DTO.UpdateUserDTO;
import com.raul.trabalhoJwt.domain.user.DTO.UserResponseDTO;
import com.raul.trabalhoJwt.domain.user.User;
import com.raul.trabalhoJwt.domain.user.UserRole;
import com.raul.trabalhoJwt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponseDTO::new)
                .collect(Collectors.toList());
    }

    public UserResponseDTO updateUser(String userId, UpdateUserDTO data) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        if (!currentUser.getId().equals(userId) && currentUser.getRole() != UserRole.ADMIN) {
            throw new RuntimeException("Not authorized to update this user");
        }

        if (data.login() != null && !data.login().isEmpty()) {
            user.setLogin(data.login());
        }

        if (data.password() != null && !data.password().isEmpty()) {

            if (data.currentPassword() != null &&
                    !passwordEncoder.matches(data.currentPassword(), currentUser.getPassword())) {
                throw new RuntimeException("Current password is incorrect");
            }
            user.setPassword(passwordEncoder.encode(data.password()));
        }

        User updatedUser = userRepository.save(user);
        return new UserResponseDTO(updatedUser);
    }

    public void deleteUser(String userId) {

        if (!userRepository.existsById(userId)) {
            throw new UsernameNotFoundException("User not found");
        }


        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        if (!currentUser.getId().equals(userId) && currentUser.getRole() != UserRole.ADMIN) {
            throw new RuntimeException("Not authorized to delete this user");
        }

        userRepository.deleteById(userId);
    }

    public UserResponseDTO getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new UserResponseDTO(user);
    }
}