package br.ufc.ativufc.dto.response.auth;

public record TokenResponse(
        String token,
        String email,
        String perfil
) {
}
