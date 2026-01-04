package br.ufc.ativufc.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AtividadeRequest(
        @NotBlank
        String nome,

        @NotBlank
        String descricao
) {
}
