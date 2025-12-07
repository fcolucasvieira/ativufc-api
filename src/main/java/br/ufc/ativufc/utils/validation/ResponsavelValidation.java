package br.ufc.ativufc.utils.validation;

import br.ufc.ativufc.exception.AlreadyExistsException;
import br.ufc.ativufc.repository.ResponsavelRepository;

public class ResponsavelValidation {
    public static void validarSiapeUnico(ResponsavelRepository repository, String siape){
        if(repository.existsBySiape(siape))
            throw new AlreadyExistsException("Responsável já cadastrado com este SIAPE");
    }
}
