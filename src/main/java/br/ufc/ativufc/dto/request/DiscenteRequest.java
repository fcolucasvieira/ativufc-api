package br.ufc.ativufc.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record DiscenteRequest(
        @NotBlank
        String matricula,

        @NotBlank
        String nome,

        @PastOrPresent
        LocalDate ingressao,

        @NotNull
        Long idCurso,

        @NotNull
        @Min(0)
        Integer horasCumpridas,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String senha
) {
}
