package br.ufc.ativufc.dto.response;

public record CursoResponse(
        Long id,
        String nome,
        Integer totalHorasComplementares
) {
}
