package br.ufc.ativufc.dto.response;

public record ResponsavelResponse(
        String cpf,
        String nome,
        String email,
        boolean ativo
) {}
