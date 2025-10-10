package br.ufc.ativufc.service;

import br.ufc.ativufc.dto.DiscenteResponse;
import br.ufc.ativufc.dto.SolicitacaoRequest;
import br.ufc.ativufc.dto.SolicitacaoResponse;
import br.ufc.ativufc.dto.StatusRequest;
import br.ufc.ativufc.model.*;
import br.ufc.ativufc.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SolicitacaoService {

    private final SolicitacaoRepository solicitacaoRepository;
    private final DiscenteRepository discenteRepository;
    private final SubtipoRepository subtipoRepository;
    private final InstituicaoRepository instituicaoRepository;

    public SolicitacaoService(
            SolicitacaoRepository solicitacaoRepository,
            DiscenteRepository discenteRepository,
            SubtipoRepository subtipoRepository,
            InstituicaoRepository instituicaoRepository
    ) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.discenteRepository = discenteRepository;
        this.subtipoRepository = subtipoRepository;
        this.instituicaoRepository = instituicaoRepository;
    }

    public SolicitacaoResponse cadastrar(SolicitacaoRequest request) {
        Discente discente = discenteRepository.findByMatricula(request.matriculaDiscente()).get();
        SubtipoAtividade subtipo = subtipoRepository.findById(request.idSubtipoAtividade()).get();
        Instituicao instituicao = instituicaoRepository.findById(request.idInstituicao()).get();

        Solicitacao solicitacao = new Solicitacao(
                null,
                discente,
                subtipo,
                instituicao,
                request.cargaHorariaTotal(),
                request.dataInicio(),
                request.dataFim(),
                java.time.LocalDate.now(),
                Status.PENDENTE,
                request.observacao(),
                null
        );

        solicitacaoRepository.save(solicitacao);

        return toResponse(solicitacao);
    }

    public List<SolicitacaoResponse> listarTodos() {
        return solicitacaoRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public List<SolicitacaoResponse> listarPorMatricula(String matricula) {
        Discente discente = discenteRepository.findByMatricula(matricula).get();
        return solicitacaoRepository.findByDiscente(discente).stream()
                .map(this::toResponse)
                .toList();
    }

    public SolicitacaoResponse atualizarStatus(Long id, StatusRequest request){
        Solicitacao solicitacao = solicitacaoRepository.findById(id).get();

        solicitacao.setStatus(request.status());
        solicitacao.setObservacaoResponsavel(request.observacaoResponsavel());
        solicitacaoRepository.save(solicitacao);

        return toResponse(solicitacao);
    }

    public SolicitacaoResponse toResponse(Solicitacao solicitacao) {
        return new SolicitacaoResponse(
                solicitacao.getId(),
                solicitacao.getDiscente().getNome(),
                solicitacao.getInstituicao().getNome(),
                solicitacao.getSubTipoAtividade().getDescricaoSubTipoAtividade(),
                solicitacao.getCargaHorariaTotal(),
                solicitacao.getStatus(),
                solicitacao.getDataSolicitacao(),
                solicitacao.getObservacaoResponsavel()
        );
    }
}
