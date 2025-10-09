package br.ufc.ativufc.service;

import br.ufc.ativufc.dto.SolicitacaoRequest;
import br.ufc.ativufc.dto.SolicitacaoResponse;
import br.ufc.ativufc.model.*;
import br.ufc.ativufc.repository.*;
import org.springframework.stereotype.Service;

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
        Discente discente = discenteRepository.findByMatricula(request.matriculaDiscente())
                .orElseThrow(() -> new RuntimeException("Discente não encontrado"));
        SubtipoAtividade subtipo = subtipoRepository.findById(request.idSubtipoAtividade()).orElseThrow();
        Instituicao instituicao = instituicaoRepository.findById(request.idInstituicao()).orElseThrow();

        SolicitacaoCreditacaoAtividade solicitacao = new SolicitacaoCreditacaoAtividade(
                null,
                discente,
                subtipo,
                instituicao,
                request.cargaHorariaTotal(),
                request.dataInicio(),
                request.dataFim(),
                java.time.LocalDate.now(),
                false,
                request.observacao()
        );

        solicitacaoRepository.save(solicitacao);

        return new SolicitacaoResponse(
                solicitacao.getId(),
                discente.getNome(),
                instituicao.getNome(),
                subtipo.getDescricaoSubTipoAtividade(),
                solicitacao.getCargaHorariaTotal(),
                solicitacao.getDeferida(),
                solicitacao.getDataSolicitacao()
        );
    }
}
