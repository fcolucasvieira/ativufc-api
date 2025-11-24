package br.ufc.ativufc.dto.response;

public record TokenResponse(
        String token,
        String email,
        String perfil
) {
}
