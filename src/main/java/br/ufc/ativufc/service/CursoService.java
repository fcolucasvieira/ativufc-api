package br.ufc.ativufc.service;

import br.ufc.ativufc.dto.request.CursoRequest;
import br.ufc.ativufc.dto.response.CursoResponse;
import br.ufc.ativufc.exception.AlreadyExistsException;
import br.ufc.ativufc.exception.NotFoundException;
import br.ufc.ativufc.model.Curso;
import br.ufc.ativufc.repository.CursoRepository;
import br.ufc.ativufc.repository.DiscenteRepository;
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

    public CursoResponse cadastrar(CursoRequest request){
        if(cursoRepository.existsByNome(request.nome()))
            throw new AlreadyExistsException("Curso já cadastrado com este nome");

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
        List<Curso> lista = cursoRepository.findAll();

        if(lista.isEmpty())
            throw new NotFoundException("Nenhum curso cadastrado");

        return lista.stream().map(this::toResponse).toList();
    }

    public CursoResponse atualizar(Long id, CursoRequest request){
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Curso não encontrado"));

        if(!curso.getNome().equals(request.nome()) && cursoRepository.existsByNome(request.nome()))
            throw new AlreadyExistsException("Já existe curso com este nome");

        curso.setNome(request.nome());
        curso.setTotalHorasComplementares(request.totalHorasComplementares());
        cursoRepository.save(curso);

        return toResponse(curso);
    }

    public void remover(Long id){
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Curso não encontrado"));

        if(discenteRepository.existsByCurso(curso)) {
            throw new IllegalStateException("Não é possível remover curso com discentes vinculados");
        }

        cursoRepository.delete(curso);
    }

    public CursoResponse toResponse(Curso curso){
        return new CursoResponse(curso.getId(),
                curso.getNome(),
                curso.getTotalHorasComplementares());
    }
}
