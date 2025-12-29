package br.ufc.ativufc.dto.response;

import br.ufc.ativufc.model.enums.Status;

import java.time.LocalDate;

public record SolicitacaoSummaryResponse(
        Long id,
        String nomeDiscente,
        String subtipoAtividade,
        Status status,
        LocalDate dataSolicitacao,
        Long comprovanteId
) {}
