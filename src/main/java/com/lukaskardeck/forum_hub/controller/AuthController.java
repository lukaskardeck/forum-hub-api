package com.lukaskardeck.forum_hub.controller;

import com.lukaskardeck.forum_hub.domain.auth.LoginUserRequest;
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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginUserRequest loginReq) {
        var token = new UsernamePasswordAuthenticationToken(loginReq.login(), loginReq.password());
        var authentication = manager.authenticate(token);

        // Aqui você pode gerar um token JWT se quiser implementar
        return ResponseEntity.ok("Autenticado com sucesso!");
    }
}
