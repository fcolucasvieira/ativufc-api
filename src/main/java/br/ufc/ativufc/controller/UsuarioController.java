package br.ufc.ativufc.controller;

import br.ufc.ativufc.dto.request.UsuarioRequest;
import br.ufc.ativufc.dto.response.UsuarioResponse;
import br.ufc.ativufc.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> cadastrar(@Valid @RequestBody UsuarioRequest request) {
        UsuarioResponse response = service.cadastrar(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {
        UsuarioResponse response = service.buscarPorId(id);

        if (response == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {
        List<UsuarioResponse> lista = service.listarTodos();

        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}/ativo")
    public ResponseEntity<UsuarioResponse> atualizarAtivo(@PathVariable Long id,
                                                          @RequestParam boolean ativo) {
        UsuarioResponse response = service.atualizarAtivo(id, ativo);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }


}
