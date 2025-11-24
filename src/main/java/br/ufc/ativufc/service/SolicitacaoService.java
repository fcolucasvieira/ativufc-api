package br.ufc.ativufc.service;

import br.ufc.ativufc.dto.request.AtualizarSolicitacaoRequest;
import br.ufc.ativufc.dto.request.SolicitacaoRequest;
import br.ufc.ativufc.dto.request.StatusRequest;
import br.ufc.ativufc.dto.response.SolicitacaoResponse;
import br.ufc.ativufc.model.*;
import br.ufc.ativufc.repository.*;
import org.springframework.stereotype.Service;

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

    public SolicitacaoResponse buscarPorId(Long id){
    Solicitacao solicitacao = solicitacaoRepository.findById(id).get();

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

    public List<SolicitacaoResponse> listarPorStatus(Status status){
        List<Solicitacao> solicitacoes = solicitacaoRepository.findByStatus(status);

        return solicitacaoRepository.findByStatus(status).stream()
                .map(this::toResponse)
                .toList();
    }

    // Simplificar metodo (Atualizações futuras)
    public SolicitacaoResponse atualizar(Long id, AtualizarSolicitacaoRequest request){
        Solicitacao solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));

        if(solicitacao.getStatus() != Status.PENDENTE)
            throw new IllegalStateException("Solicitação só pode ser editada enquanto status PENDENTE");

        solicitacao.setCargaHorariaTotal(request.cargaHorariaTotal());
        solicitacao.setDataInicio(request.dataInicio());
        solicitacao.setDataFim(request.dataFim());
        solicitacao.setObservacao(request.observacao());

        Instituicao instituicao = instituicaoRepository.findById(request.idInstituicao())
                .orElseThrow(() -> new RuntimeException("Instituição não encontrada"));
        solicitacao.setInstituicao(instituicao);

        SubtipoAtividade subtipo = subtipoRepository.findById(request.idSubtipoAtividade())
                .orElseThrow(() -> new RuntimeException("Subtipo de atividade não encontrado"));
        solicitacao.setSubTipoAtividade(subtipo);

        solicitacaoRepository.save(solicitacao);
        return toResponse(solicitacao);
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
