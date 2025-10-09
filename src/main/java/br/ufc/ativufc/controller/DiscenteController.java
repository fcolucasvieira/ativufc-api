package br.ufc.ativufc.controller;

import br.ufc.ativufc.dto.DiscenteRequest;
import br.ufc.ativufc.dto.DiscenteResponse;
import br.ufc.ativufc.service.DiscenteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/discentes")
public class DiscenteController {
    private final DiscenteService service;

    public DiscenteController(DiscenteService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DiscenteResponse> cadastrar(@Valid @RequestBody DiscenteRequest request){
        DiscenteResponse response = service.cadastrar(request);
        return ResponseEntity.ok(response);
    }
}
