package br.ufc.ativufc.controller;

import br.ufc.ativufc.dto.request.ConfiguracaoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/configuracoes")
public class ConfiguracaoController {

    // Simulação de Banco de Dados na Memória RAM (Apenas para teste)
    // Quando você reiniciar o Java, isso volta para os valores padrão.
    private static ConfiguracaoDTO configAtual = new ConfiguracaoDTO(
            120,
            120,
            120,
            LocalDate.of(2025, 2, 1),
            LocalDate.of(2025, 6, 30),
            true
    );

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDENADOR')")
    public ResponseEntity<ConfiguracaoDTO> buscarConfiguracao() {
        return ResponseEntity.ok(configAtual);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDENADOR')")
    public ResponseEntity<Void> atualizarConfiguracao(@RequestBody ConfiguracaoDTO novaConfig) {
        configAtual = novaConfig;

        System.out.println("Configurações atualizadas pelo Coordenador:");
        System.out.println("Ensino: " + novaConfig.limiteEnsino());
        System.out.println("Fim: " + novaConfig.dataFimSubmissao());

        return ResponseEntity.ok().build();
    }
}