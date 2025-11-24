package br.ufc.ativufc.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record DiscenteRequest(
        @NotBlank
        String matricula,

        @NotBlank
        String nome,

        @PastOrPresent
        LocalDate ingressao,

        @NotNull
        Integer totalHorasComplementares,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String senha
) {
}
