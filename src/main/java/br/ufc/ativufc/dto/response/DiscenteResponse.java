package br.ufc.ativufc.dto.response;

import java.time.LocalDate;

public record DiscenteResponse(
        String matricula,
        String nome,
        String email,
        LocalDate ingressao,
        String cursoNome,
        Integer totalHorasComplementares,
        Integer horasCumpridas,
        Integer horasRestantes,
        String telefone
) {}
