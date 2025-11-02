package br.ufc.ativufc.dto;

import br.ufc.ativufc.model.Perfil;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        Perfil perfil,
        Boolean ativo
) {}
