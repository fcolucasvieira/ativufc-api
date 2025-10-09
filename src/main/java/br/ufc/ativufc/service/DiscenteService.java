package br.ufc.ativufc.service;

import br.ufc.ativufc.dto.DiscenteRequest;
import br.ufc.ativufc.dto.DiscenteResponse;
import br.ufc.ativufc.model.Discente;
import br.ufc.ativufc.model.Perfil;
import br.ufc.ativufc.repository.DiscenteRepository;
import org.springframework.stereotype.Service;

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

        return new DiscenteResponse(
                discente.getMatricula(),
                discente.getNome(),
                discente.getPerfil().name()
        );
    }
}
