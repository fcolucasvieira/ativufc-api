package br.ufc.ativufc.dto.response;

import br.ufc.ativufc.model.enums.Status;
import br.ufc.ativufc.model.enums.TipoParticipacao;

import java.time.LocalDate;

public record SolicitacaoDetailResponse(
        Long id,
        String matriculaDiscente,
        String nomeDiscente,
        String nomeInstituicao,
        String subtipoAtividade,
        TipoParticipacao tipoParticipacao,
        Integer cargaHorariaTotal,
        Integer horasAproveitadas,
        LocalDate dataInicio,
        LocalDate dataFim,
        LocalDate dataSolicitacao,
        Status status,
        String observacao,
        String observacaoResponsavel,
        Long comprovanteId
) {}

