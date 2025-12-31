package br.ufc.ativufc.service;

import br.ufc.ativufc.dto.request.SolicitacaoRequest;
import br.ufc.ativufc.dto.request.AnaliseSolicitacaoRequest;
import br.ufc.ativufc.dto.response.SolicitacaoDetailResponse;
import br.ufc.ativufc.dto.response.SolicitacaoSummaryResponse;
import br.ufc.ativufc.exception.NotFoundException;
import br.ufc.ativufc.model.*;
import br.ufc.ativufc.model.enums.Status;
import br.ufc.ativufc.repository.*;
import br.ufc.ativufc.utils.validation.SolicitacaoValidation;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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

    @Transactional
    public SolicitacaoDetailResponse cadastrar(SolicitacaoRequest request) {
        // 1. Validar entidades
        Discente discente = discenteRepository.findByMatricula(request.matriculaDiscente())
                .orElseThrow(() -> new NotFoundException("Discente não encontrado"));

        Subtipo subtipo = subtipoRepository.findById(request.subtipoId())
                .orElseThrow(() -> new NotFoundException("Subtipo não encontrado"));

        Instituicao instituicao = instituicaoRepository.findById(request.instituicaoId())
                .orElseThrow(() -> new NotFoundException("Instituição não encontrada"));

        // 2. Validar dados
        SolicitacaoValidation.validarDatas(request.dataInicio(), request.dataFim());
        SolicitacaoValidation.validarCargaHoraria(request.cargaHorariaSolicitada(), subtipo);

        Solicitacao s = new Solicitacao(
                null,
                discente,
                subtipo,
                instituicao,
                request.participacao(),
                request.cargaHorariaSolicitada(),
                0,
                request.dataInicio(),
                request.dataFim(),
                LocalDate.now(),
                Status.PENDENTE,
                request.observacaoDiscente(),
                null,
                null
        );

        solicitacaoRepository.save(s);
        return toDetailResponse(s);
    }

    public SolicitacaoDetailResponse buscarPorId(Long id){
        Solicitacao s = solicitacaoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Solicitação não encontrada"));

        return toDetailResponse(s);
    }

    public List<SolicitacaoSummaryResponse> listarTodos() {
        return solicitacaoRepository.findAll().stream()
                .map(this::toSummaryResponse)
                .toList();
    }

    public List<SolicitacaoSummaryResponse> listarPorMatricula(String matricula) {
        Discente discente = discenteRepository.findByMatricula(matricula)
                .orElseThrow(() -> new NotFoundException("Discente não encontrado"));

        return solicitacaoRepository.findByDiscente(discente).stream()
                .map(this::toSummaryResponse)
                .toList();
    }

    public List<SolicitacaoSummaryResponse> listarPorStatus(Status status){
        return solicitacaoRepository.findByStatus(status).stream()
                .map(this::toSummaryResponse)
                .toList();
    }

    @Transactional
    public SolicitacaoDetailResponse atualizarStatus(Long id, AnaliseSolicitacaoRequest request){
        Solicitacao s = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Solicitação não encontrada"));

        // Atualiza status e observação de responsável
        s.setStatus(request.status());
        s.setObservacaoResponsavel(request.observacaoResponsavel());

        if(request.status() == Status.DEFERIDA){
            SolicitacaoValidation.validarHorasAproveitadas(request.cargaHorariaAproveitada(), s.getCargaHorariaSolicitada());

            s.setCargaHorariaAproveitada(request.cargaHorariaAproveitada());

            Discente discente = s.getDiscente();

            discente.setHorasCumpridas(discente.getHorasCumpridas() + s.getCargaHorariaAproveitada());
            discenteRepository.save(discente);

        } else if(request.status() == Status.INDEFERIDA){
            s.setCargaHorariaAproveitada(0);
        }

        solicitacaoRepository.save(s);
        return toDetailResponse(s);
    }

    @Transactional
    public void remover(Long id) {
        Solicitacao s = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Solicitação não encontrada"));

        SolicitacaoValidation.validarStatusRemovivel(s.getStatus());

        solicitacaoRepository.delete(s);
    }

    public SolicitacaoSummaryResponse toSummaryResponse(Solicitacao s){
        return new SolicitacaoSummaryResponse(
        s.getId(),
        s.getDiscente().getNome(),
        s.getSubtipo().getAtividade().getNome(),
        s.getSubtipo().getDescricao(),
        s.getParticipacao(),
        s.getCargaHorariaSolicitada(),
        s.getStatus(),
        s.getDataSolicitacao(),
        s.getComprovante() != null ? s.getComprovante().getId() : null
        );
    }

    public SolicitacaoDetailResponse toDetailResponse(Solicitacao s) {
        return new SolicitacaoDetailResponse(
                s.getId(),
                s.getDiscente().getNome(),
                s.getDiscente().getMatricula(),
                s.getInstituicao().getNome(),
                s.getSubtipo().getAtividade().getNome(),
                s.getSubtipo().getDescricao(),
                s.getParticipacao(),
                s.getCargaHorariaSolicitada(),
                s.getCargaHorariaAproveitada(),
                s.getDataInicio(),
                s.getDataFim(),
                s.getDataSolicitacao(),
                s.getStatus(),
                s.getObservacaoDiscente(),
                s.getObservacaoResponsavel(),
                s.getComprovante() != null ? s.getComprovante().getId() : null
        );
    }
}
