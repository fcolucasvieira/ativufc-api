package br.ufc.ativufc.repository;

import br.ufc.ativufc.model.Atividade;
import br.ufc.ativufc.model.Subtipo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubtipoRepository extends JpaRepository<Subtipo, Long> {
        boolean existsByDescricao(String descricao);
}
