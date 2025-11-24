package br.ufc.ativufc.dto.response;

public record InstituicaoResponse(
        Long id,
        String nome,
        String cnpj,
        String endereco
) {}
