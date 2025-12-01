package br.ufc.ativufc.controller;

import br.ufc.ativufc.dto.request.InstituicaoRequest;
import br.ufc.ativufc.dto.response.InstituicaoResponse;
import br.ufc.ativufc.service.InstituicaoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    public ResponseEntity<InstituicaoResponse> buscarPorId(@PathVariable Long id) {
        InstituicaoResponse response = service.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<InstituicaoResponse>> listarTodas(){
        List<InstituicaoResponse> lista = service.listarTodas();
        return ResponseEntity.ok(lista);
    }
}
