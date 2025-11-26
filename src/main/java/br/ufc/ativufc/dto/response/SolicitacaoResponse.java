package br.ufc.ativufc.dto.response;

import br.ufc.ativufc.model.Status;
import br.ufc.ativufc.model.TipoParticipacao;

import java.time.LocalDate;

public record SolicitacaoResponse(
        Long id,
        String nomeDiscente,
        String nomeInstituicao,
        String subtipoAtividade,
        TipoParticipacao tipoParticipacao,
        Integer cargaHorariaTotal,
        Integer horasAproveitadas,
        LocalDate dataSolicitacao,
        Status status,
        String observacaoResponsavel,
        String comprovantePath
) {}
