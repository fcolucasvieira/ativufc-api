package br.ufc.ativufc.dto.request;

import br.ufc.ativufc.model.enums.Perfil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UsuarioRequest(
        @NotBlank
        String nome,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String senha,

        @NotBlank
        @Pattern(regexp = "^(\\d{10,11}|\\(\\d{2}\\)\\s?\\d{4,5}-\\d{4})$",
        message = "Telefone deve ter 10 ou 11 dígitos numéricos ou estar no formato (XX) XXXXX-XXXX"
        )
        String telefone,

        @NotNull
        Perfil perfil,

        @NotNull
        Boolean ativo
        ) {}
