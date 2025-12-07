package br.ufc.ativufc.utils.validation;


import br.ufc.ativufc.exception.AlreadyExistsException;
import br.ufc.ativufc.repository.DiscenteRepository;

public class DiscenteValidation {
    public static void validarMatriculaUnica(DiscenteRepository repository, String matricula){
        if(repository.existsByMatricula(matricula))
            throw new AlreadyExistsException("Discente já cadastrado com esta matrícula");
    }
}
