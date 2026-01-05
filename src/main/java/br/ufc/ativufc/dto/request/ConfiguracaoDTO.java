package br.ufc.ativufc.dto.request;

import java.time.LocalDate;

public record ConfiguracaoDTO(
        Integer limiteEnsino,
        Integer limitePesquisa,
        Integer limiteExtensao,
        LocalDate dataInicioSubmissao,
        LocalDate dataFimSubmissao,
        Boolean permitirAtraso
) {}