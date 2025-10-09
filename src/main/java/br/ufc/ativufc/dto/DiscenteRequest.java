package br.ufc.ativufc.dto;

import jakarta.validation.constraints.NotBlank;

public record DiscenteRequest(
        @NotBlank
        String matricula,

        @NotBlank
        String nome,

        @NotBlank
        String senha
) {
}
