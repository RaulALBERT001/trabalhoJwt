package com.raul.trabalhoJwt.repositories;

import com.raul.trabalhoJwt.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    UserDetails findByLogin(String login);
    Optional<User> findById(String id);
}
