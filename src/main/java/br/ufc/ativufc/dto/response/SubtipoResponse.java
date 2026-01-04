package br.ufc.ativufc.dto.response;

public record SubtipoResponse(
    Long id,
    String descricao,
    Integer horasMin,
    Integer horasMax,
    String atividadeNome
) {}
