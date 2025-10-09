package br.ufc.ativufc.dto;

import jakarta.validation.constraints.NotBlank;

public record InstituicaoRequest(
        @NotBlank
        String nome,

        String cnpj,
        String endereco
) {}
