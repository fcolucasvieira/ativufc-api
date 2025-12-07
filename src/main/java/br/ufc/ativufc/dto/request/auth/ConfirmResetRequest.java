package br.ufc.ativufc.dto.request.auth;

public record ConfirmResetRequest(String token, String novaSenha) {
}
