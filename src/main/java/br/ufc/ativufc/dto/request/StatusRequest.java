package br.ufc.ativufc.dto.request;

import br.ufc.ativufc.model.Status;
import jakarta.validation.constraints.NotNull;

public record StatusRequest(
        @NotNull
        Status status,

        String observacaoResponsavel
) {
}
