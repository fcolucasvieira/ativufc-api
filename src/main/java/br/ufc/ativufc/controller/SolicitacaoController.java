package br.ufc.ativufc.controller;

import br.ufc.ativufc.dto.SolicitacaoRequest;
import br.ufc.ativufc.dto.SolicitacaoResponse;
import br.ufc.ativufc.service.SolicitacaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/solicitacao")
public class SolicitacaoController {
    private final SolicitacaoService service;

    public SolicitacaoController(SolicitacaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SolicitacaoResponse> cadastrar(@Valid @RequestBody SolicitacaoRequest request) {
        return ResponseEntity.ok(service.cadastrar(request));
    }
}
