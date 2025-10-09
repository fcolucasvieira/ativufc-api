package br.ufc.ativufc.dto;

import java.time.LocalDate;

public record SolicitacaoResponse(
        Long id,
        String nomeDiscente,
        String nomeInstituicao,
        String subtipoAtividade,
        Integer cargaHorariaTotal,
        Boolean deferida,
        LocalDate dataSolicitacao
) { }
