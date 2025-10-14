package br.ufc.ativufc.service;

import br.ufc.ativufc.dto.SubtipoRequest;
import br.ufc.ativufc.dto.SubtipoResponse;
import br.ufc.ativufc.model.SubtipoAtividade;
import br.ufc.ativufc.repository.SubtipoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubtipoService {
    private final SubtipoRepository repository;

    public SubtipoService(SubtipoRepository repository) {
        this.repository = repository;
    }

    public SubtipoResponse cadastrar(SubtipoRequest request) {
        SubtipoAtividade subtipo = new SubtipoAtividade(
                null,
                request.descricaoSubTipoAtividade(),
                request.cargaHorariaMaxima());

        repository.save(subtipo);

        return toResponse(subtipo);
    }

    public SubtipoResponse buscarPorId(Long id) {
        return repository.findById(id)
                .map(this::toResponse)
                .orElse(null);
    }


    public List<SubtipoResponse> listarTodos() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public SubtipoResponse toResponse(SubtipoAtividade subtipo) {
        return new SubtipoResponse(
                subtipo.getId(),
                subtipo.getDescricaoSubTipoAtividade(),
                subtipo.getCargaHorariaMaxima()
        );
    }
}
