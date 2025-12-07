package br.ufc.ativufc.utils.validation;

import br.ufc.ativufc.exception.AlreadyExistsException;
import br.ufc.ativufc.repository.InstituicaoRepository;

public class InstituicaoValidation {
    public static void validarCnpjUnico(InstituicaoRepository repository, String cnpj){
        if (repository.existsByCnpj(cnpj))
            throw new AlreadyExistsException("Instituição já cadastrada com este CNPJ");
    }

    public static void validarNomeUnico(InstituicaoRepository repository, String nome){
        if(repository.existsByNome(nome))
            throw new AlreadyExistsException("Instituição já cadastrada com este nome");
    }
}
