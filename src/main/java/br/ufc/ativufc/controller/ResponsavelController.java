package br.ufc.ativufc.controller;

import br.ufc.ativufc.dto.request.ResponsavelRequest;
import br.ufc.ativufc.dto.response.ResponsavelResponse;
import br.ufc.ativufc.service.ResponsavelService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/responsaveis")
public class ResponsavelController {
    private final ResponsavelService service;

    public ResponsavelController(ResponsavelService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ResponsavelResponse> cadastrar(@Valid @RequestBody ResponsavelRequest request) {
        ResponsavelResponse response = service.cadastrar(request);
        URI location = URI.create("/responsaveis/" + response.siape());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{siape}")
    @PreAuthorize("hasRole('RESPONSAVEL') and @securityUtil.isResponsavelOwner(#siape)")
    public ResponseEntity<ResponsavelResponse> buscarPorSiape(@PathVariable String siape){
        ResponsavelResponse response = service.buscarPorSiape(siape);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ResponsavelResponse>> listarTodos(){
        List<ResponsavelResponse> lista = service.listarTodos();
        return ResponseEntity.ok(lista);
    }
}
