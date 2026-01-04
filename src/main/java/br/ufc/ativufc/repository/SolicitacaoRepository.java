package br.ufc.ativufc.repository;

import br.ufc.ativufc.model.*;
import br.ufc.ativufc.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {
    List<Solicitacao> findByDiscente(Discente discente);
    List<Solicitacao> findByStatus(Status status);
    boolean existsByInstituicao(Instituicao instituicao);
    boolean existsBySubtipo(Subtipo subtipo);

}
