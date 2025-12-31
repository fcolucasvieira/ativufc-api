package br.ufc.ativufc.utils.validation;

import br.ufc.ativufc.exception.OperationNotAllowedException;
import br.ufc.ativufc.model.Subtipo;
import br.ufc.ativufc.model.enums.Status;

import java.time.LocalDate;

public class SolicitacaoValidation {

    public static void validarDatas(LocalDate inicio, LocalDate fim) {
        if (fim.isBefore(inicio)) {
            throw new OperationNotAllowedException("Data fim não pode ser anterior à data início");
        }
    }

    public static void validarCargaHoraria(Integer cargaHorariaSolicitada, Subtipo subtipo) {
        if (cargaHorariaSolicitada < subtipo.getHorasMin() || cargaHorariaSolicitada > subtipo.getHorasMax()) {
            throw new OperationNotAllowedException("Carga horária total deve ficar dentro do limite permitido");
        }
    }

    public static void validarStatusEditavel(Status status) {
        if (status != Status.PENDENTE) {
            throw new OperationNotAllowedException("Solicitação já analisada não pode ser editada");
        }
    }

    public static void validarStatusRemovivel(Status status) {
        if (status != Status.PENDENTE) {
            throw new OperationNotAllowedException("Só é possível remover solicitações pendentes");
        }
    }

    public static void validarHorasAproveitadas(Integer cargaHorariaAproveitada, Integer cargaHorariaSolicitada) {
        if (cargaHorariaAproveitada == null) {
            throw new OperationNotAllowedException("Carga horária aproveitada deve ser informada ao deferir solicitação");
        }
        if (cargaHorariaAproveitada > cargaHorariaSolicitada) {
            throw new OperationNotAllowedException("Carga horária aproveitada não pode exceder a carga horária solicitada");
        }
    }
}
