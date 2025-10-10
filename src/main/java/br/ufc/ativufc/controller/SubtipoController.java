package br.ufc.ativufc.controller;

import br.ufc.ativufc.dto.SubtipoRequest;
import br.ufc.ativufc.dto.SubtipoResponse;
import br.ufc.ativufc.service.SubtipoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.ok(response);
    }
}
