package br.ufc.ativufc.utils.validation;

import br.ufc.ativufc.exception.AlreadyExistsException;
import br.ufc.ativufc.exception.OperationNotAllowedException;
import br.ufc.ativufc.repository.SubtipoRepository;

public class SubtipoValidation {
    public static void validarDescricaoUnica(SubtipoRepository repository, String descricao) {
        if (repository.existsByDescricao(descricao))
            throw new AlreadyExistsException("Subtipo já cadastrado com esta descrição");
    }

    public static void validarHoras(Integer horasMin, Integer horasMax){
        if(horasMin > horasMax)
            throw new OperationNotAllowedException("Horas mínimas não podem ser maiores que horas máximas");
    }
}
