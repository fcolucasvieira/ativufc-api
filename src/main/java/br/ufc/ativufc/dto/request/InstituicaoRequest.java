package br.ufc.ativufc.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

public record InstituicaoRequest(

        @NotBlank
        String nome,

        @CNPJ
        @NotBlank
        String cnpj,

        @NotBlank
        String endereco
) {}
