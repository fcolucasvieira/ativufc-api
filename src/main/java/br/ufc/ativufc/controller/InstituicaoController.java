package br.ufc.ativufc.controller;

import br.ufc.ativufc.dto.InstituicaoRequest;
import br.ufc.ativufc.dto.InstituicaoResponse;
import br.ufc.ativufc.service.InstituicaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/instituicoes")
public class InstituicaoController {
    private final InstituicaoService service;

    public InstituicaoController(InstituicaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<InstituicaoResponse> cadastrar(@Valid @RequestBody InstituicaoRequest request) {
        InstituicaoResponse response = service.cadastrar(request);
        return ResponseEntity.ok(response);
    }
}
