package br.ufc.ativufc.dto.response;

import br.ufc.ativufc.model.enums.Status;
import br.ufc.ativufc.model.enums.TipoParticipacao;

import java.time.LocalDate;

public record SolicitacaoSummaryResponse(
        Long id,
        String nomeDiscente,
        String nomeAtividade,
        String nomeSubtipo,
        TipoParticipacao participacao,
        Integer cargaHorariaSolicitada,
        Status status,
        LocalDate dataSolicitacao,
        Long comprovanteId
) {}
