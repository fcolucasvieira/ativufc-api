package br.ufc.ativufc.service;

import br.ufc.ativufc.dto.request.InstituicaoRequest;
import br.ufc.ativufc.dto.request.update.UpdateInstituicaoRequest;
import br.ufc.ativufc.dto.response.InstituicaoResponse;
import br.ufc.ativufc.exception.NotFoundException;
import br.ufc.ativufc.exception.OperationNotAllowedException;
import br.ufc.ativufc.model.Instituicao;
import br.ufc.ativufc.repository.InstituicaoRepository;
import br.ufc.ativufc.repository.SolicitacaoRepository;
import br.ufc.ativufc.utils.validation.InstituicaoValidation;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstituicaoService {
    private final InstituicaoRepository instituicaoRepository;
    private final SolicitacaoRepository solicitacaoRepository;

    public InstituicaoService(InstituicaoRepository instituicaoRepository, SolicitacaoRepository solicitacaoRepository) {
        this.instituicaoRepository = instituicaoRepository;
        this.solicitacaoRepository = solicitacaoRepository;
    }

    @Transactional
    public InstituicaoResponse cadastrar(InstituicaoRequest request) {
        InstituicaoValidation.validarCnpjUnico(instituicaoRepository, request.cnpj());
        InstituicaoValidation.validarNomeUnico(instituicaoRepository, request.nome());

        Instituicao instituicao = new Instituicao(null,
                request.nome(),
                request.cnpj(),
                request.endereco());

        instituicaoRepository.save(instituicao);

        return toResponse(instituicao);
    }

    public InstituicaoResponse buscarPorId(Long id) {
        Instituicao instituicao = instituicaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Instituição não encontrada"));
        return toResponse(instituicao);
    }

    public List<InstituicaoResponse> listarTodas() {
        return instituicaoRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public InstituicaoResponse atualizar(Long id, UpdateInstituicaoRequest request){
        Instituicao instituicao = instituicaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Instituição não encontrada"));

        if(request.endereco() != null && !request.endereco().isBlank())
            instituicao.setEndereco(request.endereco());

        instituicaoRepository.save(instituicao);
        return toResponse(instituicao);
    }

    @Transactional
    public void remover(Long id){
        Instituicao instituicao = instituicaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Instituição não encontrada"));

        boolean vinculoSolicitacao = solicitacaoRepository.existsByInstituicao(instituicao);
        if(vinculoSolicitacao)
            throw new OperationNotAllowedException("Instituição vinculada a solicitações não pode ser removida");

        instituicaoRepository.delete(instituicao);
    }

    public InstituicaoResponse toResponse(Instituicao instituicao) {
        return new InstituicaoResponse(instituicao.getId(),
                instituicao.getNome(),
                instituicao.getCnpj(),
                instituicao.getEndereco());
    }

}
