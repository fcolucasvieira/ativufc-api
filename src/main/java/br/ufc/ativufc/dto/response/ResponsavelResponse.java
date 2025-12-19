package br.ufc.ativufc.dto.response;

import br.ufc.ativufc.model.enums.Cargo;

public record ResponsavelResponse(
        String siape,
        String nome,
        String instituicaoNome,
        Cargo cargo,
        String email,
        boolean ativo
) {}
