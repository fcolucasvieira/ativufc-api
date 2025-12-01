package br.ufc.ativufc.dto.request;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record AtualizarDiscenteRequest(
        String nome,

        @PastOrPresent
        LocalDate ingressao,

        Long idCurso
) {}
