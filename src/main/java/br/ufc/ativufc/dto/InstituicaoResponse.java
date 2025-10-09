package br.ufc.ativufc.dto;

public record InstituicaoResponse(
        Long id,
        String nome,
        String cnpj,
        String endereco
) {}
