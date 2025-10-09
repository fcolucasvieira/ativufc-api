package br.ufc.ativufc.dto;

public record SubtipoResponse(
    Long id,
    String descricaoSubTipoAtividade,
    Integer cargaHorariaMaxima
) {}
