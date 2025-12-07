package br.ufc.ativufc.service;

import br.ufc.ativufc.dto.request.SubtipoRequest;
import br.ufc.ativufc.dto.response.SubtipoResponse;
import br.ufc.ativufc.exception.AlreadyExistsException;
import br.ufc.ativufc.exception.NotFoundException;
import br.ufc.ativufc.model.SubtipoAtividade;
import br.ufc.ativufc.repository.SubtipoRepository;
import br.ufc.ativufc.utils.validation.SubtipoValidation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubtipoService {
    private final SubtipoRepository repository;

    public SubtipoService(SubtipoRepository repository) {
        this.repository = repository;
    }

    public SubtipoResponse cadastrar(SubtipoRequest request) {
        SubtipoValidation.validarDescricaoUnica(repository, request.descricaoSubTipoAtividade());

        SubtipoAtividade subtipo = new SubtipoAtividade(
                null,
                request.descricaoSubTipoAtividade(),
                request.cargaHorariaMaxima());

        repository.save(subtipo);
        return toResponse(subtipo);
    }

    public SubtipoResponse buscarPorId(Long id) {
        SubtipoAtividade subtipo = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subtipo não encontrado"));
        return toResponse(subtipo);
    }

    public List<SubtipoResponse> listarTodos() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public SubtipoResponse atualizar(Long id, SubtipoRequest request) {
        SubtipoAtividade subtipo = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subtipo não encontrado"));

        if (request.descricaoSubTipoAtividade() != null &&
                !subtipo.getDescricaoSubTipoAtividade().equals(request.descricaoSubTipoAtividade())) {

            SubtipoValidation.validarDescricaoUnica(repository, request.descricaoSubTipoAtividade());

            subtipo.setDescricaoSubTipoAtividade(request.descricaoSubTipoAtividade());
        }

        if (request.cargaHorariaMaxima() != null) {
            subtipo.setCargaHorariaMaxima(request.cargaHorariaMaxima());
        }

        repository.save(subtipo);
        return toResponse(subtipo);
    }


    public void remover(Long id) {
        SubtipoAtividade subtipo = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subtipo não encontrado"));

        repository.delete(subtipo);
    }

    public SubtipoResponse toResponse(SubtipoAtividade subtipo) {
        return new SubtipoResponse(
                subtipo.getId(),
                subtipo.getDescricaoSubTipoAtividade(),
                subtipo.getCargaHorariaMaxima()
        );
    }
}
