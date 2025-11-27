package br.ufc.ativufc.repository;

import br.ufc.ativufc.model.Discente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiscenteRepository extends JpaRepository<Discente, Long> {

    Optional<Discente> findByMatricula(String matricula);

    boolean existsByMatricula(String matricula);
}
