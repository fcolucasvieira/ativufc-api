package br.ufc.ativufc.dto.request;

import br.ufc.ativufc.model.enums.Status;
import jakarta.validation.constraints.NotNull;

public record StatusRequest(
        @NotNull
        Status status,

        Integer horasAproveitadas,

        String observacaoResponsavel
) {
}
