package br.ufc.ativufc.controller;

import br.ufc.ativufc.dto.request.AtualizarSolicitacaoRequest;
import br.ufc.ativufc.dto.request.SolicitacaoRequest;
import br.ufc.ativufc.dto.response.SolicitacaoResponse;
import br.ufc.ativufc.dto.request.StatusRequest;
import br.ufc.ativufc.model.Status;
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

    @GetMapping("/{id}")
    public ResponseEntity<SolicitacaoResponse> buscarPorId(@PathVariable Long id) {
        SolicitacaoResponse response = service.buscarPorId(id);
        return ResponseEntity.ok(response);
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

    @GetMapping("/status")
    public ResponseEntity<List<SolicitacaoResponse>> listarPorStatus(@RequestParam Status status) {
        List<SolicitacaoResponse> lista = service.listarPorStatus(status);
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SolicitacaoResponse> atualizar(@PathVariable Long id, @Valid @RequestBody AtualizarSolicitacaoRequest request) {
        SolicitacaoResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<SolicitacaoResponse> atualizarStatus(@PathVariable Long id, @Valid @RequestBody StatusRequest request) {
        SolicitacaoResponse response = service.atualizarStatus(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id){
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
