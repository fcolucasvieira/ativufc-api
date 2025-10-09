package br.ufc.ativufc.repository;

import br.ufc.ativufc.model.SolicitacaoCreditacaoAtividade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitacaoRepository extends JpaRepository<SolicitacaoCreditacaoAtividade, Long> {}
