package br.ufc.ativufc.service;

import br.ufc.ativufc.dto.request.SubtipoRequest;
import br.ufc.ativufc.dto.response.SubtipoResponse;
import br.ufc.ativufc.exception.NotFoundException;
import br.ufc.ativufc.exception.OperationNotAllowedException;
import br.ufc.ativufc.model.Atividade;
import br.ufc.ativufc.model.Subtipo;
import br.ufc.ativufc.repository.AtividadeRepository;
import br.ufc.ativufc.repository.SolicitacaoRepository;
import br.ufc.ativufc.repository.SubtipoRepository;
import br.ufc.ativufc.utils.validation.SubtipoValidation;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubtipoService {
    private final SubtipoRepository subtipoRepository;
    private final AtividadeRepository atividadeRepository;
    private final SolicitacaoRepository solicitacaoRepository;

    public SubtipoService(SubtipoRepository subtipoRepository, AtividadeRepository atividadeRepository, SolicitacaoRepository solicitacaoRepository) {
        this.subtipoRepository = subtipoRepository;
        this.atividadeRepository = atividadeRepository;
        this.solicitacaoRepository = solicitacaoRepository;
    }

    @Transactional
    public SubtipoResponse cadastrar(SubtipoRequest request) {
        Atividade atividade = atividadeRepository.findById(request.atividadeId())
                        .orElseThrow(() -> new NotFoundException("Atividade não encontrada"));

        SubtipoValidation.validarDescricaoUnica(subtipoRepository, request.descricao());
        SubtipoValidation.validarHoras(request.horasMin(), request.horasMax());

        Subtipo subtipo = new Subtipo(
                null,
                request.descricao(),
                request.horasMin(),
                request.horasMax(),
                atividade
        );

        subtipoRepository.save(subtipo);
        return toResponse(subtipo);
    }

    public SubtipoResponse buscarPorId(Long id) {
        Subtipo subtipo = subtipoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subtipo não encontrado"));
        return toResponse(subtipo);
    }

    public List<SubtipoResponse> listarTodos() {
        return subtipoRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public List<SubtipoResponse> listarPorAtividade(Long atividadeId) {
        Atividade atividade = atividadeRepository.findById(atividadeId)
                .orElseThrow(() -> new NotFoundException("Atividade não encontrada"));

        return atividade.getSubtipos().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public void remover(Long id) {
        Subtipo subtipo = subtipoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subtipo não encontrado"));

        boolean vinculoSolicitacao = solicitacaoRepository.existsBySubtipo(subtipo);
        if(vinculoSolicitacao)
            throw new OperationNotAllowedException("Subtipo vinculado a solicitações não pode ser removido");

        subtipoRepository.delete(subtipo);
    }

    private SubtipoResponse toResponse(Subtipo subtipo) {
        return new SubtipoResponse(
                subtipo.getId(),
                subtipo.getDescricao(),
                subtipo.getHorasMin(),
                subtipo.getHorasMax(),
                subtipo.getAtividade().getNome()
        );
    }
}
