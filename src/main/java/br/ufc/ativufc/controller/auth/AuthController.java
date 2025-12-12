package br.ufc.ativufc.controller.auth;

import br.ufc.ativufc.dto.request.auth.LoginRequest;
import br.ufc.ativufc.dto.response.auth.ResetSenhaResponse;
import br.ufc.ativufc.dto.response.jwt.TokenResponse;
import br.ufc.ativufc.service.auth.AuthenticationService;
import br.ufc.ativufc.dto.request.auth.ConfirmResetRequest;
import br.ufc.ativufc.dto.request.auth.ResetPasswordRequest;
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

    @PostMapping("/reset-password/request")
    public ResponseEntity<String> requestReset(@RequestBody ResetPasswordRequest request){
        String token = authService.iniciarResetSenha(request.email());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/reset-password/confirm")
    public ResponseEntity<ResetSenhaResponse> confirmReset(@RequestBody ConfirmResetRequest request){
        ResetSenhaResponse response = authService.concluirResetSenha(request.token(), request.novaSenha());
        return ResponseEntity.ok(response);
    }
}
