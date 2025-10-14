package br.ufc.ativufc.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record AtualizarSolicitacaoRequest(
        @NotNull
        Long idSubtipoAtividade,

        @NotNull
        Long idInstituicao,

        @NotNull
        @Min(1)
        Integer cargaHorariaTotal,

        @NotNull
        @FutureOrPresent
        LocalDate dataInicio,

        @NotNull
        @Future
        LocalDate dataFim,

        @NotBlank
        String observacao
) {
}
