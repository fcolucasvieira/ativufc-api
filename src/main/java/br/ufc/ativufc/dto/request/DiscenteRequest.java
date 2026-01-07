package br.ufc.ativufc.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record DiscenteRequest(
        @NotBlank
        String matricula,

        @PastOrPresent
        LocalDate ingressao,

        @NotNull
        Long idCurso,

        @NotNull
        @Min(0)
        Integer horasCumpridas,

        @NotBlank
        String nome,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String senha,

        @NotBlank(message = "O telefone é obrigatório")
        @Pattern(regexp = "^(\\d{10,11}|\\(\\d{2}\\)\\s?\\d{4,5}-\\d{4})$",
                 message = "Telefone deve ter 10 ou 11 dígitos numéricos ou estar no formato (XX) XXXXX-XXXX")
        String telefone


) {
}
