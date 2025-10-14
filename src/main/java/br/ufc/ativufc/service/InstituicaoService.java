package br.ufc.ativufc.service;

import br.ufc.ativufc.dto.InstituicaoRequest;
import br.ufc.ativufc.dto.InstituicaoResponse;
import br.ufc.ativufc.model.Instituicao;
import br.ufc.ativufc.repository.InstituicaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstituicaoService {
    private final InstituicaoRepository repository;

    public InstituicaoService(InstituicaoRepository repository) {
        this.repository = repository;
    }

    public InstituicaoResponse cadastrar(InstituicaoRequest request) {
        Instituicao instituicao = new Instituicao(
                null,
                request.nome(),
                request.cnpj(),
                request.endereco()
        );

        repository.save(instituicao);

        return toResponse(instituicao);
    }

    public InstituicaoResponse buscarPorId(Long id){
        return repository.findById(id)
                .map(this::toResponse)
                .orElse(null);
    }

    public List<InstituicaoResponse> listarTodos(){
        return repository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public InstituicaoResponse toResponse(Instituicao instituicao) {
        return new InstituicaoResponse(
                instituicao.getId(),
                instituicao.getNome(),
                instituicao.getCnpj(),
                instituicao.getEndereco()
        );
    }

}
