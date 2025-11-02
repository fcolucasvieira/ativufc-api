package br.ufc.ativufc.dto;

import br.ufc.ativufc.model.Perfil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequest(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank String senha,
        @NotNull Perfil perfil,
        @NotNull Boolean ativo
        ) {}
