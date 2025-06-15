package com.raul.trabalhoJwt.domain.user.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserDTO(
        @NotBlank(message = "Login cannot be blank")
        @Size(min = 3, message = "Login must be at least 3 characters long")
        String login,

        @Size(min = 6, message = "Password must be at least 6 characters long")
        String password,

        String currentPassword
) {}