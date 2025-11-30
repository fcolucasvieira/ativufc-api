package br.ufc.ativufc.repository;

import br.ufc.ativufc.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    boolean existsByNome(String nome);
}
