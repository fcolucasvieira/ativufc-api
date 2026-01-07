package br.ufc.ativufc.dto.response;

import br.ufc.ativufc.model.enums.Perfil;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        String telefone,
        Perfil perfil,
        Boolean ativo
) {}
