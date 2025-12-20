package br.ufc.ativufc.controller;

import br.ufc.ativufc.dto.request.InstituicaoRequest;
import br.ufc.ativufc.dto.response.InstituicaoResponse;
import br.ufc.ativufc.service.InstituicaoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/instituicoes")
public class InstituicaoController {
    private final InstituicaoService service;

    public InstituicaoController(InstituicaoService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InstituicaoResponse> cadastrar(@Valid @RequestBody InstituicaoRequest request) {
        InstituicaoResponse response = service.cadastrar(request);
        URI location = URI.create("/instituicoes/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DISCENTE','RESPONSAVEL','ADMIN')")
    public ResponseEntity<InstituicaoResponse> buscarPorId(@PathVariable Long id) {
        InstituicaoResponse response = service.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DISCENTE','RESPONSAVEL','ADMIN')")
    public ResponseEntity<List<InstituicaoResponse>> listarTodas(){
        List<InstituicaoResponse> lista = service.listarTodas();
        return ResponseEntity.ok(lista);
    }

    // fazer PUT E DELETE (próxs atualizações)
}
