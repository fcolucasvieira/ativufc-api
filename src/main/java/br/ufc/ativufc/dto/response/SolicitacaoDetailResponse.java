package br.ufc.ativufc.dto.response;

import br.ufc.ativufc.model.enums.Status;
import br.ufc.ativufc.model.enums.TipoParticipacao;
import java.time.LocalDate;

public record SolicitacaoDetailResponse(
        Long id,
        String nomeDiscente,
        String matriculaDiscente,
        String nomeInstituicao,
        String nomeAtividade,
        String nomeSubtipo,
        TipoParticipacao participacao,
        Integer cargaHorariaSolicitada,
        Integer cargaHorariaAproveitada,
        LocalDate dataInicio,
        LocalDate dataFim,
        LocalDate dataSolicitacao,
        Status status,
        String observacaoDiscente,
        String observacaoResponsavel,
        Long comprovanteId
) {}
