package br.ufc.ativufc.repository;

import br.ufc.ativufc.model.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
    boolean existsByNome(String nome);
}
