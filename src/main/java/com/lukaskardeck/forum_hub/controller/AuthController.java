package com.lukaskardeck.forum_hub.controller;

import com.lukaskardeck.forum_hub.dto.LoginUserRequest;
import com.lukaskardeck.forum_hub.domain.User;
import com.lukaskardeck.forum_hub.dto.TokenResponse;
import com.lukaskardeck.forum_hub.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginUserRequest loginReq) {

        // 1. Cria token de autenticação com email/senha
        var authToken = new UsernamePasswordAuthenticationToken(loginReq.login(), loginReq.password());

        // 2. Autentica o usuário
        var authentication = manager.authenticate(authToken);

        // 3. Gera o token JWT
        var token = tokenService.generateToken((User) authentication.getPrincipal());

        // Aqui você pode gerar um token JWT se quiser implementar
        return ResponseEntity.ok(new TokenResponse(token));
    }
}
