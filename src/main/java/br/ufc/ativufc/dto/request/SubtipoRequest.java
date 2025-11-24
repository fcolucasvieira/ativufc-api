package br.ufc.ativufc.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SubtipoRequest(
        @NotBlank
        String descricaoSubTipoAtividade,

        @NotNull
        @Min(1)
        Integer cargaHorariaMaxima
) {}
