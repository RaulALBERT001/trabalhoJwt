package com.raul.trabalhoJwt.domain.user.DTO;

import com.raul.trabalhoJwt.domain.user.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {


}
