package br.ufc.ativufc.controller;

import br.ufc.ativufc.dto.DiscenteRequest;
import br.ufc.ativufc.dto.DiscenteResponse;
import br.ufc.ativufc.service.DiscenteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<DiscenteResponse> buscarPorMatricula(@PathVariable String matricula) {
        DiscenteResponse response = service.buscarPorMatricula(matricula);

        if (response == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<DiscenteResponse>> listarTodos() {
        List<DiscenteResponse> lista = service.listarTodos();
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{matricula}")
    public ResponseEntity<DiscenteResponse> atualizar(@PathVariable String matricula, @Valid @RequestBody DiscenteRequest request) {
        DiscenteResponse response = service.atualizar(matricula, request);
        return ResponseEntity.ok(response);
    }
}
