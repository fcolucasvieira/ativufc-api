package br.ufc.ativufc.dto.request.update;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUsuarioRequest(
        String nome,

        @Email
        String email,

        @Pattern(regexp = "^(\\d{10,11}|\\(\\d{2}\\)\\s?\\d{4,5}-\\d{4})$",
                message = "Telefone deve ter 10 ou 11 dígitos numéricos ou estar no formato (XX) XXXXX-XXXX"
        )
        String telefone
) {}
