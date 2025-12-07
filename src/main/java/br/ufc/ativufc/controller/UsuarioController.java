package br.ufc.ativufc.controller;

import br.ufc.ativufc.dto.request.UsuarioRequest;
import br.ufc.ativufc.dto.response.UsuarioResponse;
import br.ufc.ativufc.model.enums.Perfil;
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

    // Cadastro de ADMIN
    @PostMapping("/admin")
    public ResponseEntity<UsuarioResponse> cadastrarAdmin(@Valid @RequestBody UsuarioRequest request) {
        UsuarioResponse response = service.cadastrarAdmin(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioResponse> buscarPorEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.buscarPorEmail(email));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/status/{ativo}")
    public ResponseEntity<List<UsuarioResponse>> listarPorAtivo(@PathVariable boolean ativo) {
        return ResponseEntity.ok(service.listarPorAtivo(ativo));
    }

    @PutMapping("/{id}/ativo")
    public ResponseEntity<UsuarioResponse> atualizarAtivo(@PathVariable Long id, @RequestParam boolean ativo) {
        return ResponseEntity.ok(service.atualizarAtivo(id, ativo));
    }

    @PutMapping("/{id}/perfil")
    public ResponseEntity<UsuarioResponse> atualizarPerfil(@PathVariable Long id, @RequestParam Perfil perfil) {
        return ResponseEntity.ok(service.atualizarPerfil(id, perfil));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
