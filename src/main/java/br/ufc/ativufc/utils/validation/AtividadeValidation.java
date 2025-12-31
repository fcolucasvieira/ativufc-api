package br.ufc.ativufc.utils.validation;

import br.ufc.ativufc.exception.AlreadyExistsException;

import br.ufc.ativufc.model.Atividade;
import br.ufc.ativufc.repository.AtividadeRepository;

import br.ufc.ativufc.repository.SubtipoRepository;

public class AtividadeValidation {
    public static void validarNomeUnico(AtividadeRepository repository, String nome) {
        if (repository.existsByNome(nome))
            throw new AlreadyExistsException("Já existe uma atividade com este nome");
    }

    public static void validarAtividadeSemSubtipos(Atividade atividade) {
        if(atividade.getSubtipos() != null && !atividade.getSubtipos().isEmpty())
            throw new IllegalStateException("Não é possível remover atividade com subtipos vinculados");
    }
}
