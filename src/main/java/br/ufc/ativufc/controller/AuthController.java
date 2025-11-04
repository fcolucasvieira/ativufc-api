package br.ufc.ativufc.controller;

import br.ufc.ativufc.dto.LoginRequest;
import br.ufc.ativufc.security.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationService service;

    public AuthController(AuthenticationService service){
        this.service = service;
    }

    // Endpoint de login que autentica o usuário e retorna o token JWT
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request){
        String token = service.autenticar(request.email(), request.senha());

        return ResponseEntity.ok(token);
    }


}
