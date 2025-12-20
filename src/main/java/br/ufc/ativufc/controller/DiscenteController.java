package br.ufc.ativufc.controller;

import br.ufc.ativufc.dto.request.update.UpdateDiscenteRequest;
import br.ufc.ativufc.dto.request.DiscenteRequest;
import br.ufc.ativufc.dto.response.DiscenteResponse;
import br.ufc.ativufc.service.DiscenteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/discentes")
public class DiscenteController {
    private final DiscenteService service;

    public DiscenteController(DiscenteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DiscenteResponse> cadastrar(@Valid @RequestBody DiscenteRequest request) {
        DiscenteResponse response = service.cadastrar(request);
        URI location = URI.create("/discentes/" + response.matricula());
        return ResponseEntity.created(location).body(response);
    }


    @GetMapping("/{matricula}")
    @PreAuthorize("(hasRole('DISCENTE') and @securityUtil.isDiscenteOwner(#matricula)) or hasRole('RESPONSAVEL')")
    public ResponseEntity<DiscenteResponse> buscarPorMatricula(@PathVariable String matricula) {
        DiscenteResponse response = service.buscarPorMatricula(matricula);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DiscenteResponse>> listarTodos() {
        List<DiscenteResponse> lista = service.listarTodos();
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{matricula}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DiscenteResponse> atualizar(@PathVariable String matricula, @Valid @RequestBody UpdateDiscenteRequest request) {
        DiscenteResponse response = service.atualizar(matricula, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{matricula}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> remover(@PathVariable String matricula){
        service.remover(matricula);
        return ResponseEntity.noContent().build();
    }
}
