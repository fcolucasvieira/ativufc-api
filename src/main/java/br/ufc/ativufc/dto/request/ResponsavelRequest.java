package br.ufc.ativufc.dto.request;

import br.ufc.ativufc.model.enums.Cargo;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
        String senha,

        @NotBlank(message = "O telefone é obrigatório")
        @Pattern(regexp = "^(\\d{10,11}|\\(\\d{2}\\)\\s?\\d{4,5}-\\d{4})$",
                message = "Telefone deve ter 10 ou 11 dígitos numéricos ou estar no formato (XX) XXXXX-XXXX")
        String telefone
) {}
