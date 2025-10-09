package br.ufc.ativufc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SubtipoRequest(
        @NotBlank
        String descricaoSubTipoAtividade,

        @NotNull
        Integer cargaHorariaMaxima
) {}
