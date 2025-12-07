package br.ufc.ativufc.utils.validation;

import br.ufc.ativufc.exception.AlreadyExistsException;
import br.ufc.ativufc.exception.OperationNotAllowedException;
import br.ufc.ativufc.model.Curso;
import br.ufc.ativufc.repository.CursoRepository;
import br.ufc.ativufc.repository.DiscenteRepository;

public class CursoValidation {
    public static void validarNomeUnico(CursoRepository repository, String nome){
        if(repository.existsByNome(nome))
            throw new AlreadyExistsException("Curso já cadastrado com este nome");
    }

    public static void validarHorasCumpridas(Curso curso, Integer horasCumpridas) {
        if (horasCumpridas != null && horasCumpridas > curso.getTotalHorasComplementares())
            throw new OperationNotAllowedException("Horas cumpridas iniciais não podem exceder o total de horas complementares do curso");
    }

    public static void validarDeleteSemDiscentes(DiscenteRepository repository, Curso curso){
        if(repository.existsByCurso(curso)) {
            throw new IllegalStateException("Não é possível remover curso com discentes vinculados");
        }
    }
}
