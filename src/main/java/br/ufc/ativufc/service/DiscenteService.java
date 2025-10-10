package br.ufc.ativufc.service;

import br.ufc.ativufc.dto.DiscenteRequest;
import br.ufc.ativufc.dto.DiscenteResponse;
import br.ufc.ativufc.model.Discente;
import br.ufc.ativufc.model.Perfil;
import br.ufc.ativufc.repository.DiscenteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscenteService {
    private final DiscenteRepository repository;

    public DiscenteService(DiscenteRepository repository) {
        this.repository = repository;
    }

    public DiscenteResponse cadastrar(DiscenteRequest request) {
        Discente discente = new Discente(
                request.matricula(),
                request.nome(),
                java.time.LocalDate.now(),
                0,
                request.senha(),
                Perfil.DISCENTE
        );

        repository.save(discente);

        return toResponse(discente);
    }

    public List<DiscenteResponse> listarTodos() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public DiscenteResponse buscarPorMatricula(String matricula) {
        Discente discente = repository.findByMatricula(matricula).get();
        return toResponse(discente);
    }

    public DiscenteResponse atualizar(String matricula, DiscenteRequest request) {
        Discente discente = repository.findByMatricula(matricula).get();

        discente.setNome(request.nome());
        discente.setSenha(request.senha());

        repository.save(discente);

        return toResponse(discente);
    }

    public DiscenteResponse toResponse(Discente discente){
        return new DiscenteResponse(
                discente.getMatricula(),
                discente.getNome(),
                discente.getPerfil().name()
        );
    }
}
