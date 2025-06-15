package com.raul.trabalhoJwt.domain.user.DTO;

import com.raul.trabalhoJwt.domain.user.User;

public record UserResponseDTO(String id, String login, String role){

    public UserResponseDTO(User user) {
        this(user.getId(), user.getLogin(), user.getRole().getRole());
    }
}


