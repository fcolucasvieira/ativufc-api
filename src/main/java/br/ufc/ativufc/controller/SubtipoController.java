package br.ufc.ativufc.controller;

import br.ufc.ativufc.dto.request.SubtipoRequest;
import br.ufc.ativufc.dto.response.SubtipoResponse;
import br.ufc.ativufc.service.SubtipoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/subtipos")
public class SubtipoController {
    private final SubtipoService service;

    public SubtipoController(SubtipoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SubtipoResponse> cadastrar(@Valid @RequestBody SubtipoRequest request) {
        SubtipoResponse response = service.cadastrar(request);
        URI location = URI.create("/subtipos/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubtipoResponse> buscarPorId(@PathVariable Long id){
        SubtipoResponse response = service.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<SubtipoResponse>> listarTodos() {
        List<SubtipoResponse> lista = service.listarTodos();
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubtipoResponse> atualizar(@PathVariable Long id, @Valid @RequestBody SubtipoRequest request){
        SubtipoResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id){
        service.remover(id);
        return ResponseEntity.noContent().build();
    }


}
