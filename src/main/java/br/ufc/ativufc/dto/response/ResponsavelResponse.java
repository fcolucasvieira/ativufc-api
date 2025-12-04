package br.ufc.ativufc.dto.response;

public record ResponsavelResponse(
        String siape,
        String nome,
        String email,
        boolean ativo
) {}
