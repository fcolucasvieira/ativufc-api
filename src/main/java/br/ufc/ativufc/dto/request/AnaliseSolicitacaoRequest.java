package br.ufc.ativufc.dto.request;

import br.ufc.ativufc.model.enums.Status;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AnaliseSolicitacaoRequest(
        @NotNull
        Status status,

        @Min(0)
        Integer cargaHorariaAproveitada,

        @Size(max = 350)
        String observacaoResponsavel
) {
}
