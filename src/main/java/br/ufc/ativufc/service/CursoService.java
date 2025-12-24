package br.ufc.ativufc.service;

import br.ufc.ativufc.dto.request.CursoRequest;
import br.ufc.ativufc.dto.response.CursoResponse;
import br.ufc.ativufc.exception.AlreadyExistsException;
import br.ufc.ativufc.exception.NotFoundException;
import br.ufc.ativufc.model.Curso;
import br.ufc.ativufc.repository.CursoRepository;
import br.ufc.ativufc.repository.DiscenteRepository;
import br.ufc.ativufc.utils.validation.CursoValidation;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {
    private final CursoRepository cursoRepository;
    private final DiscenteRepository discenteRepository;

    public CursoService(CursoRepository cursoRepository, DiscenteRepository discenteRepository){
        this.cursoRepository = cursoRepository;
        this.discenteRepository = discenteRepository;
    }

    @Transactional
    public CursoResponse cadastrar(CursoRequest request){
        CursoValidation.validarNomeUnico(cursoRepository, request.nome());

        Curso curso = new Curso(
                null,
                request.nome(),
                request.totalHorasComplementares()
        );

        cursoRepository.save(curso);
        return toResponse(curso);
    }

    public CursoResponse buscarPorId(Long id){
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Curso não encontrado"));
        return toResponse(curso);
    }

    public List<CursoResponse> listarTodos(){
        return cursoRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public CursoResponse atualizar(Long id, CursoRequest request){
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Curso não encontrado"));

        if (!curso.getNome().equals(request.nome()))
            CursoValidation.validarNomeUnico(cursoRepository, request.nome());

        curso.setNome(request.nome());
        curso.setTotalHorasComplementares(request.totalHorasComplementares());
        cursoRepository.save(curso);

        return toResponse(curso);
    }

    @Transactional
    public void remover(Long id){
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Curso não encontrado"));

        CursoValidation.validarDeleteSemDiscentes(discenteRepository, curso);

        cursoRepository.delete(curso);
    }

    public CursoResponse toResponse(Curso curso){
        return new CursoResponse(curso.getId(),
                curso.getNome(),
                curso.getTotalHorasComplementares());
    }
}
