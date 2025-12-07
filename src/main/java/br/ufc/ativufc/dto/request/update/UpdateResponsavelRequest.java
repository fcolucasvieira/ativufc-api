package br.ufc.ativufc.dto.request.update;

import jakarta.validation.constraints.NotBlank;

public record UpdateResponsavelRequest(
        @NotBlank
        String nome) {
}
