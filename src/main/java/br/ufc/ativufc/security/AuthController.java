package br.ufc.ativufc.security;

import br.ufc.ativufc.dto.request.LoginRequest;
import br.ufc.ativufc.dto.response.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authService;

    public AuthController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        TokenResponse response = authService.autenticar(request.email(), request.senha());
        return ResponseEntity.ok(response);
    }
}
