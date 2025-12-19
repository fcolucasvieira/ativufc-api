package br.ufc.ativufc.dto.request.update;

import br.ufc.ativufc.model.Instituicao;
import br.ufc.ativufc.model.enums.Cargo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateResponsavelRequest(
        String nome,

        @NotNull
        Long idInstituicao,

        Cargo cargo
) {
}
