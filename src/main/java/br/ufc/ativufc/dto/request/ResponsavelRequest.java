package br.ufc.ativufc.dto.request;

import br.ufc.ativufc.model.enums.Cargo;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record ResponsavelRequest(
        @NotBlank
        String siape,

        @NotBlank
        String nome,

        @NotNull
        Long idInstituicao,

        @NotNull
        Cargo cargo,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String senha
) {}
