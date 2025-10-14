package br.ufc.ativufc.repository;

import br.ufc.ativufc.dto.SolicitacaoResponse;
import br.ufc.ativufc.model.Discente;
import br.ufc.ativufc.model.Solicitacao;
import br.ufc.ativufc.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {
    List<Solicitacao> findByDiscente(Discente discente);
    List<Solicitacao> findByStatus(Status status);
}
