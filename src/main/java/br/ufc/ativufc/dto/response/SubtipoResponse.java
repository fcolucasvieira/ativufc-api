package br.ufc.ativufc.dto.response;

public record SubtipoResponse(
    Long id,
    String descricaoSubTipoAtividade,
    Integer cargaHorariaMaxima
) {}
