package br.ufc.ativufc.dto.response.jwt;

public record TokenResponse(
        String token,
        String email,
        String perfil,
        Object identificador
) {
}
