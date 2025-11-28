package br.ufc.ativufc.service;

import br.ufc.ativufc.dto.request.InstituicaoRequest;
import br.ufc.ativufc.dto.response.InstituicaoResponse;
import br.ufc.ativufc.exception.AlreadyExistsException;
import br.ufc.ativufc.exception.NotFoundException;
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
        if (repository.existsByCnpj(request.cnpj()))
            throw new AlreadyExistsException("Instituição com este CNPJ já cadastrada");

        if(repository.existsByNome(request.nome()))
            throw new AlreadyExistsException("Instituição com este nome já cadastrada");

        Instituicao instituicao = new Instituicao(null,
                request.nome(),
                request.cnpj(),
                request.endereco());

        repository.save(instituicao);

        return toResponse(instituicao);
    }

    public InstituicaoResponse buscarPorId(Long id) {
        Instituicao instituicao = repository.findById(id).orElseThrow(() -> new NotFoundException("Instituição não encontrada"));
        return toResponse(instituicao);
    }

    public List<InstituicaoResponse> listarTodas() {
        List<Instituicao> lista = repository.findAll();
        if (lista.isEmpty()) throw new NotFoundException("Nenhuma instituição cadastrada");

        return lista.stream().map(this::toResponse).toList();
    }

    public InstituicaoResponse toResponse(Instituicao instituicao) {
        return new InstituicaoResponse(instituicao.getId(),
                instituicao.getNome(),
                instituicao.getCnpj(),
                instituicao.getEndereco());
    }

}
