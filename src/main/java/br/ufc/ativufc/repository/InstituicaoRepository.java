package br.ufc.ativufc.repository;

import br.ufc.ativufc.model.Instituicao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstituicaoRepository extends JpaRepository<Instituicao, Long> {
    boolean existsByCnpj(String cnpj);

    boolean existsByNome(String nome);
}
