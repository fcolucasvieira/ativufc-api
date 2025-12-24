package br.ufc.ativufc.dto.request.update;

import br.ufc.ativufc.model.enums.TipoParticipacao;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UpdateSolicitacaoRequest(
        Long idSubtipoAtividade,

        Long idInstituicao,

        @Min(1)
        Integer cargaHorariaTotal,

        @PastOrPresent
        LocalDate dataInicio,

        @PastOrPresent
        LocalDate dataFim,

        TipoParticipacao tipoParticipacao,

        @Size(max = 350)
        String observacao
) {
}
