package br.ufc.ativufc.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SubtipoRequest(
        @NotBlank
        String descricao,

        @NotNull
        @Min(0)
        Integer horasMin,

        @NotNull
        @Min(1)
        Integer horasMax,

        @NotNull
        Long atividadeId
) {}
