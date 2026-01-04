package br.ufc.ativufc.service;

import br.ufc.ativufc.dto.request.AtividadeRequest;
import br.ufc.ativufc.dto.response.AtividadeResponse;
import br.ufc.ativufc.exception.NotFoundException;
import br.ufc.ativufc.model.Atividade;
import br.ufc.ativufc.repository.AtividadeRepository;
import br.ufc.ativufc.utils.validation.AtividadeValidation;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtividadeService {
    private final AtividadeRepository repository;

    public AtividadeService (AtividadeRepository repository){
        this.repository = repository;
    }

    @Transactional
    public AtividadeResponse cadastrar(AtividadeRequest request){
        AtividadeValidation.validarNomeUnico(repository, request.nome());

        Atividade atividade = new Atividade(
                null,
                request.nome(),
                request.descricao(),
                null);

        repository.save(atividade);
        return toResponse(atividade);
    }

    public AtividadeResponse buscarPorId(Long id){
        Atividade atividade = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Atividade não encontrada"));

        return toResponse(atividade);
    }

    public List<AtividadeResponse> listarTodos(){
        return repository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public void remover(Long id){
        Atividade atividade = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Atividade não encontrada"));

        AtividadeValidation.validarAtividadeSemSubtipos(atividade);

        repository.delete(atividade);
    }


    private AtividadeResponse toResponse(Atividade atividade){
        return new AtividadeResponse(
                atividade.getId(),
                atividade.getNome(),
                atividade.getDescricao()
        );
    }
}
