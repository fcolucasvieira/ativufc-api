package br.ufc.ativufc.dto;

import br.ufc.ativufc.model.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StatusRequest(
        @NotNull
        Status status,

        String observacaoResponsavel
) {
}
