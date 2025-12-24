package br.ufc.ativufc.service;

import br.ufc.ativufc.dto.request.update.UpdateSolicitacaoRequest;
import br.ufc.ativufc.dto.request.SolicitacaoRequest;
import br.ufc.ativufc.dto.request.StatusRequest;
import br.ufc.ativufc.dto.response.ComprovanteResponse;
import br.ufc.ativufc.dto.response.SolicitacaoResponse;
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
    private final ComprovanteService comprovanteService;

    public SolicitacaoService(
            SolicitacaoRepository solicitacaoRepository,
            DiscenteRepository discenteRepository,
            SubtipoRepository subtipoRepository,
            InstituicaoRepository instituicaoRepository,
            ComprovanteService comprovanteService

    ) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.discenteRepository = discenteRepository;
        this.subtipoRepository = subtipoRepository;
        this.instituicaoRepository = instituicaoRepository;
        this.comprovanteService = comprovanteService;
    }

    @Transactional
    public SolicitacaoResponse cadastrar(SolicitacaoRequest request) {
        Discente discente = discenteRepository.findByMatricula(request.matriculaDiscente())
                .orElseThrow(() -> new NotFoundException("Discente não encontrado"));

        SubtipoAtividade subtipo = subtipoRepository.findById(request.idSubtipoAtividade())
                .orElseThrow(() -> new NotFoundException("Subtipo de atividade não encontrado"));

        Instituicao instituicao = instituicaoRepository.findById(request.idInstituicao())
                .orElseThrow(() -> new NotFoundException("Instituição não encontrada"));

        SolicitacaoValidation.validarDatas(request.dataInicio(), request.dataFim());
        SolicitacaoValidation.validarCargaHoraria(request.cargaHorariaTotal(), subtipo);

        Solicitacao solicitacao = new Solicitacao(
                null,
                discente,
                subtipo,
                instituicao,
                request.tipoParticipacao(),
                request.cargaHorariaTotal(),
                null,
                request.dataInicio(),
                request.dataFim(),
                java.time.LocalDate.now(),
                Status.PENDENTE,
                request.observacao(),
                null,
                null
        );

        solicitacaoRepository.save(solicitacao);
        return toResponse(solicitacao);
    }

    public SolicitacaoResponse buscarPorId(Long id){
        Solicitacao solicitacao = solicitacaoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Solicitação não encontrada"));

        return toResponse(solicitacao);
    }

    public List<SolicitacaoResponse> listarTodos() {
        return solicitacaoRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public List<SolicitacaoResponse> listarPorMatricula(String matricula) {
        Discente discente = discenteRepository.findByMatricula(matricula)
                .orElseThrow(() -> new NotFoundException("Discente não encontrado"));

        return solicitacaoRepository.findByDiscente(discente).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<SolicitacaoResponse> listarPorStatus(Status status){
        return solicitacaoRepository.findByStatus(status).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public SolicitacaoResponse atualizar(Long id, UpdateSolicitacaoRequest request) {
        Solicitacao solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Solicitação não encontrada"));

        SolicitacaoValidation.validarStatusEditavel(solicitacao.getStatus());

        if (request.cargaHorariaTotal() != null) {
            SolicitacaoValidation.validarCargaHoraria(request.cargaHorariaTotal(), solicitacao.getSubTipoAtividade());
            solicitacao.setCargaHorariaTotal(request.cargaHorariaTotal());
        }

        LocalDate novaDataInicio = request.dataInicio() != null ? request.dataInicio() : solicitacao.getDataInicio();
        LocalDate novaDataFim = request.dataFim() != null ? request.dataFim() : solicitacao.getDataFim();

        if (novaDataInicio != null && novaDataFim != null) {
            SolicitacaoValidation.validarDatas(novaDataInicio, novaDataFim);
        }

        if (request.dataInicio() != null)
            solicitacao.setDataInicio(request.dataInicio());
        if (request.dataFim() != null)
            solicitacao.setDataFim(request.dataFim());
        if (request.tipoParticipacao() != null)
            solicitacao.setTipoParticipacao(request.tipoParticipacao());
        if (request.observacao() != null && !request.observacao().isBlank())
            solicitacao.setObservacao(request.observacao());

        // Atualização de instituição
        if (request.idInstituicao() != null) {
            Instituicao instituicao = instituicaoRepository.findById(request.idInstituicao())
                    .orElseThrow(() -> new NotFoundException("Instituição não encontrada"));
            solicitacao.setInstituicao(instituicao);
        }

        // Atualização de subtipo com validação
        if (request.idSubtipoAtividade() != null) {
            SubtipoAtividade subtipo = subtipoRepository.findById(request.idSubtipoAtividade())
                    .orElseThrow(() -> new NotFoundException("Subtipo de atividade não encontrado"));
            SolicitacaoValidation.validarCargaHoraria(solicitacao.getCargaHorariaTotal(), subtipo);
            solicitacao.setSubTipoAtividade(subtipo);
        }

        solicitacaoRepository.save(solicitacao);
        return toResponse(solicitacao);
    }



    @Transactional
    public SolicitacaoResponse atualizarStatus(Long id, StatusRequest request){
        Solicitacao solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Solicitação não encontrada"));

        solicitacao.setStatus(request.status());
        solicitacao.setObservacaoResponsavel(request.observacaoResponsavel());

        if(request.status() == Status.DEFERIDA){
            SolicitacaoValidation.validarHorasAproveitadas(request.horasAproveitadas(), solicitacao.getCargaHorariaTotal());
            solicitacao.setHorasAproveitadas(request.horasAproveitadas());

            Discente discente = solicitacao.getDiscente();
            discente.setHorasCumpridas(discente.getHorasCumpridas() + solicitacao.getHorasAproveitadas());
            discenteRepository.save(discente);
        } else if(request.status() == Status.INDEFERIDA){
            solicitacao.setHorasAproveitadas(0);
        }

        solicitacaoRepository.save(solicitacao);
        return toResponse(solicitacao);
    }

    @Transactional
    public void remover(Long id) {
        Solicitacao solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Solicitação não encontrada"));

        SolicitacaoValidation.validarStatusRemovivel(solicitacao.getStatus());

        solicitacaoRepository.delete(solicitacao);
    }


    public SolicitacaoResponse toResponse(Solicitacao solicitacao) {
        ComprovanteResponse comprovanteResponse = null;
        if (solicitacao.getComprovante() != null)
            comprovanteResponse = comprovanteService.toResponse(solicitacao.getComprovante());

        return new SolicitacaoResponse(
                solicitacao.getId(),
                solicitacao.getDiscente().getMatricula(),
                solicitacao.getDiscente().getNome(),
                solicitacao.getInstituicao().getNome(),
                solicitacao.getSubTipoAtividade().getDescricaoSubTipoAtividade(),
                solicitacao.getTipoParticipacao(),
                solicitacao.getCargaHorariaTotal(),
                solicitacao.getHorasAproveitadas(),
                solicitacao.getDataInicio(),
                solicitacao.getDataFim(),
                solicitacao.getDataSolicitacao(),
                solicitacao.getStatus(),
                solicitacao.getObservacao(),
                solicitacao.getObservacaoResponsavel(),
                comprovanteResponse
        );
    }
}
