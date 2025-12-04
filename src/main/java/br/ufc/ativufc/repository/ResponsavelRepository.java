package br.ufc.ativufc.repository;

import br.ufc.ativufc.model.Responsavel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResponsavelRepository extends JpaRepository<Responsavel, String> {
    Optional<Responsavel> findBySiape(String siape);

    boolean existsBySiape(String siape);
}
