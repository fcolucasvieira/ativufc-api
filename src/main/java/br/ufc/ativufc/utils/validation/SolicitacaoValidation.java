package br.ufc.ativufc.utils.validation;

import br.ufc.ativufc.exception.OperationNotAllowedException;
import br.ufc.ativufc.model.SubtipoAtividade;
import br.ufc.ativufc.model.enums.Status;

import java.time.LocalDate;

public class SolicitacaoValidation {

    public static void validarDatas(LocalDate inicio, LocalDate fim) {
        if (fim.isBefore(inicio)) {
            throw new OperationNotAllowedException("Data fim não pode ser anterior à data início");
        }
    }

    public static void validarCargaHoraria(Integer cargaHorariaTotal, SubtipoAtividade subtipo) {
        if (cargaHorariaTotal > subtipo.getCargaHorariaMaxima()) {
            throw new OperationNotAllowedException("Carga horária total não pode exceder o limite permitido");
        }
    }

    public static void validarStatusEditavel(Status status) {
        if (status != Status.PENDENTE) {
            throw new OperationNotAllowedException("Solicitação só pode ser editada enquanto status estiver PENDENTE");
        }
    }

    public static void validarStatusRemovivel(Status status) {
        if (status != Status.PENDENTE) {
            throw new OperationNotAllowedException("Só é possível remover solicitações com status PENDENTE");
        }
    }

    public static void validarHorasAproveitadas(Integer horasAproveitadas, Integer cargaHorariaTotal) {
        if (horasAproveitadas == null) {
            throw new OperationNotAllowedException("Horas aproveitadas devem ser informadas ao deferir a solicitação");
        }
        if (horasAproveitadas > cargaHorariaTotal) {
            throw new OperationNotAllowedException("Horas aproveitadas não podem exceder a carga horária total");
        }
    }
}
