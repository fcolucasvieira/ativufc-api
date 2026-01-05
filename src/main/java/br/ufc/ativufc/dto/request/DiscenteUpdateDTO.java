package br.ufc.ativufc.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DiscenteUpdateDTO(

        // Adicionamos o campo nome que estava faltando
        String nome,

        @NotBlank(message = "O e-mail não pode estar vazio")
        @Email(message = "Formato de e-mail inválido")
        String email,

        @NotBlank(message = "O telefone é obrigatório")
        String telefone
) {}