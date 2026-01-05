package br.ufc.ativufc.controller;

import br.ufc.ativufc.dto.request.SolicitacaoRequest;
import br.ufc.ativufc.dto.response.SolicitacaoDetailResponse;
import br.ufc.ativufc.dto.request.AnaliseSolicitacaoRequest;
import br.ufc.ativufc.dto.response.SolicitacaoSummaryResponse;
import br.ufc.ativufc.model.enums.Status;
import br.ufc.ativufc.service.SolicitacaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/solicitacoes")
public class SolicitacaoController {
    private final SolicitacaoService service;

    public SolicitacaoController(SolicitacaoService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasRole('DISCENTE') and @securityUtil.isDiscenteOwner(#request.matriculaDiscente)")
    public ResponseEntity<SolicitacaoDetailResponse> cadastrar(@Valid @RequestBody SolicitacaoRequest request) {
        SolicitacaoDetailResponse response = service.cadastrar(request);
        URI location = URI.create("/solicitacoes/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("(hasRole('DISCENTE') and @securityUtil.isSolicitacaoOwner(#id)) or hasRole('RESPONSAVEL') or hasRole('ADMIN') or hasRole('COORDENADOR')")
    public ResponseEntity<SolicitacaoDetailResponse> buscarPorId(@PathVariable Long id) {
        SolicitacaoDetailResponse response = service.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('RESPONSAVEL') or hasRole('ADMIN') or hasRole('COORDENADOR')")
    public ResponseEntity<List<SolicitacaoSummaryResponse>> listarTodos() {
        List<SolicitacaoSummaryResponse> lista = service.listarTodos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/discente/{matricula}")
    @PreAuthorize("(hasRole('DISCENTE') and @securityUtil.isDiscenteOwner(#matricula)) or hasRole('RESPONSAVEL') or hasRole('ADMIN') or hasRole('COORDENADOR')")
    public ResponseEntity<List<SolicitacaoSummaryResponse>> listarPorMatricula(@PathVariable String matricula) {
        List<SolicitacaoSummaryResponse> lista = service.listarPorMatricula(matricula);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/status")
    @PreAuthorize("hasRole('RESPONSAVEL') or hasRole('ADMIN') or hasRole('COORDENADOR')")
    public ResponseEntity<List<SolicitacaoSummaryResponse>> listarPorStatus(@RequestParam Status status) {
        List<SolicitacaoSummaryResponse> lista = service.listarPorStatus(status);
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('RESPONSAVEL') or hasRole('COORDENADOR')")
    public ResponseEntity<SolicitacaoDetailResponse> atualizarStatus(@PathVariable Long id, @Valid @RequestBody AnaliseSolicitacaoRequest request) {
        SolicitacaoDetailResponse response = service.atualizarStatus(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}