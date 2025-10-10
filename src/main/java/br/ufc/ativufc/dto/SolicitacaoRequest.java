package br.ufc.ativufc.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record SolicitacaoRequest(
        @NotBlank
        String matriculaDiscente,

        @NotNull
        Long idSubtipoAtividade,

        @NotNull
        Long idInstituicao,

        @NotNull
        @Min(1)
        Integer cargaHorariaTotal,

        @NotNull
        LocalDate dataInicio,

        @NotNull
        LocalDate dataFim,

        @NotBlank
        String observacao
) {}
