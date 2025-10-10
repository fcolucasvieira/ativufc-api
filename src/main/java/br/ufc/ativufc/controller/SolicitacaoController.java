package br.ufc.ativufc.controller;

import br.ufc.ativufc.dto.SolicitacaoRequest;
import br.ufc.ativufc.dto.SolicitacaoResponse;
import br.ufc.ativufc.dto.StatusRequest;
import br.ufc.ativufc.service.SolicitacaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solicitacoes")
public class SolicitacaoController {
    private final SolicitacaoService service;

    public SolicitacaoController(SolicitacaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SolicitacaoResponse> cadastrar(@Valid @RequestBody SolicitacaoRequest request) {
        return ResponseEntity.ok(service.cadastrar(request));
    }

    @GetMapping
    public ResponseEntity<List<SolicitacaoResponse>> listarTodos() {
        List<SolicitacaoResponse> lista = service.listarTodos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/discente/{matricula}")
    public ResponseEntity<List<SolicitacaoResponse>> listarPorMatricula(@PathVariable String matricula) {
        List<SolicitacaoResponse> lista = service.listarPorMatricula(matricula);
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<SolicitacaoResponse> atualizarStatus(@PathVariable Long id, @RequestBody @Valid StatusRequest request) {
        SolicitacaoResponse response = service.atualizarStatus(id, request);
        return ResponseEntity.ok(response);
    }
}
