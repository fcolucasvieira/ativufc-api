package br.ufc.ativufc.controller;

import br.ufc.ativufc.dto.request.UsuarioRequest;
import br.ufc.ativufc.dto.response.UsuarioResponse;
import br.ufc.ativufc.model.enums.Perfil;
import br.ufc.ativufc.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponse> cadastrarAdmin(@Valid @RequestBody UsuarioRequest request) {
        UsuarioResponse response = service.cadastrarAdmin(request);
        URI location = URI.create("/usuarios/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponse> buscarPorEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.buscarPorEmail(email));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/status/{ativo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioResponse>> listarPorAtivo(@PathVariable boolean ativo) {
        return ResponseEntity.ok(service.listarPorAtivo(ativo));
    }

    @PutMapping("/{id}/ativo")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponse> atualizarAtivo(@PathVariable Long id, @RequestParam boolean ativo) {
        return ResponseEntity.ok(service.atualizarAtivo(id, ativo));
    }

    @PutMapping("/{id}/perfil")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponse> atualizarPerfil(@PathVariable Long id, @RequestParam Perfil perfil) {
        return ResponseEntity.ok(service.atualizarPerfil(id, perfil));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
