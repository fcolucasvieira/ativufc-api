package br.ufc.ativufc.dto.request;

import br.ufc.ativufc.model.TipoParticipacao;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record AtualizarSolicitacaoRequest(
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
        String observacao,

        String comprovantePath
) {
}
