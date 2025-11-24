package br.ufc.ativufc.dto.response;

import br.ufc.ativufc.model.Status;

import java.time.LocalDate;

public record SolicitacaoResponse(
        Long id,
        String nomeDiscente,
        String nomeInstituicao,
        String subtipoAtividade,
        Integer cargaHorariaTotal,
        Status status,
        LocalDate dataSolicitacao,
        String observacaoResponsavel
) {}
