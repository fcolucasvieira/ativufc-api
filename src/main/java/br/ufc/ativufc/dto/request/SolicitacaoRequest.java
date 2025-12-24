package br.ufc.ativufc.dto.request;

import br.ufc.ativufc.model.enums.TipoParticipacao;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record SolicitacaoRequest(
        @NotBlank
        String matriculaDiscente,

        @NotNull
        Long idSubtipoAtividade,

        @NotNull
        Long idInstituicao,

        @NotNull
        TipoParticipacao tipoParticipacao,

        @NotNull
        @Min(1)
        Integer cargaHorariaTotal,

        @NotNull
        @PastOrPresent
        LocalDate dataInicio,

        @NotNull
        @PastOrPresent
        LocalDate dataFim,

        @NotBlank
        @Size(max = 350)
        String observacao
) {}
