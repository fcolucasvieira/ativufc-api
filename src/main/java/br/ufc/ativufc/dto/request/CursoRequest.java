package br.ufc.ativufc.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CursoRequest(
        @NotBlank
        String nome,

        @NotNull
        @Min(0)
        Integer totalHorasComplementares
) {
}
