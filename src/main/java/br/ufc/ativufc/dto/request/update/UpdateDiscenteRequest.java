package br.ufc.ativufc.dto.request.update;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record UpdateDiscenteRequest(
        String nome,

        @PastOrPresent
        LocalDate ingressao,

        Long idCurso
) {}
