package br.ufc.ativufc.controller;

import br.ufc.ativufc.dto.request.AtividadeRequest;
import br.ufc.ativufc.dto.response.AtividadeResponse;
import br.ufc.ativufc.dto.response.SubtipoResponse;
import br.ufc.ativufc.service.AtividadeService;
import br.ufc.ativufc.service.SubtipoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/atividades")
public class AtividadeController {
    private final AtividadeService atividadeService;
    private final SubtipoService subtipoService;

    public AtividadeController(AtividadeService atividadeService, SubtipoService subtipoService) {
        this.atividadeService = atividadeService;
        this.subtipoService = subtipoService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AtividadeResponse> cadastrar(@Valid @RequestBody AtividadeRequest request) {
        AtividadeResponse response = atividadeService.cadastrar(request);
        URI location = URI.create("/atividades/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DISCENTE','RESPONSAVEL','ADMIN')")
    public ResponseEntity<AtividadeResponse> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(atividadeService.buscarPorId(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DISCENTE','RESPONSAVEL','ADMIN')")
    public ResponseEntity<List<AtividadeResponse>> listarTodos() {
        List<AtividadeResponse> lista = atividadeService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}/subtipos")
    @PreAuthorize("hasAnyRole('DISCENTE','RESPONSAVEL','ADMIN')")
    public ResponseEntity<List<SubtipoResponse>> listarSubtiposPorAtividade(@PathVariable Long id) {
        List<SubtipoResponse> lista = subtipoService.listarPorAtividade(id);
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> remover(@PathVariable Long id){
        atividadeService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
