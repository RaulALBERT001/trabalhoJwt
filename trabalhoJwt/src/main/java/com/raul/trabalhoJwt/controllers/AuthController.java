package com.raul.trabalhoJwt.controllers;


import com.raul.trabalhoJwt.core.security.TokenService;
import com.raul.trabalhoJwt.domain.user.DTO.AuthenticationDTO;
import com.raul.trabalhoJwt.domain.user.DTO.LoginResponseDTO;
import com.raul.trabalhoJwt.domain.user.DTO.RegisterDTO;
import com.raul.trabalhoJwt.domain.user.User;
import com.raul.trabalhoJwt.repositories.UserRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO authenticationDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());
        var auth =  this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO registerDTO) {
        try {
            logger.info("Tentativa de registro para o usuário: {}", registerDTO.login());

            if (this.userRepository.findByLogin(registerDTO.login()) != null) {
                logger.warn("Tentativa de registrar usuário já existente: {}", registerDTO.login());
                return ResponseEntity.badRequest().body("Usuário já existe");
            }

            String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
            User newUser = new User(registerDTO.login(), encryptedPassword, registerDTO.role());

            this.userRepository.save(newUser);
            logger.info("Usuário registrado com sucesso: {}", registerDTO.login());

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Erro ao registrar usuário", e);
            return ResponseEntity.internalServerError().body("Erro interno ao processar o registro");
        }
    }
}
