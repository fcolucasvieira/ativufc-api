package br.ufc.ativufc.repository;

import br.ufc.ativufc.model.SubtipoAtividade;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubtipoRepository extends JpaRepository<SubtipoAtividade, Long> {
        boolean existsByDescricaoSubTipoAtividade(String descricaoSubTipoAtividade);
}
