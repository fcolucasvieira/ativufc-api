package br.ufc.ativufc.utils.validation;

import br.ufc.ativufc.exception.AlreadyExistsException;
import br.ufc.ativufc.repository.SubtipoRepository;

public class SubtipoValidation {
    public static void validarDescricaoUnica(SubtipoRepository repository, String descricao) {
        if (repository.existsByDescricaoSubTipoAtividade(descricao))
            throw new AlreadyExistsException("Subtipo já cadastrado com esta descrição");
    }
}
