package br.ufc.ativufc.controller;

import br.ufc.ativufc.dto.request.ResponsavelRequest;
import br.ufc.ativufc.dto.request.update.UpdateResponsavelRequest;
import br.ufc.ativufc.dto.response.ResponsavelResponse;
import br.ufc.ativufc.service.ResponsavelService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{siape}")
    public ResponseEntity<ResponsavelResponse> buscarPorSiape(@PathVariable String siape){
        ResponsavelResponse response = service.buscarPorSiape(siape);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ResponsavelResponse>> listarTodos(){
        List<ResponsavelResponse> lista = service.listarTodos();
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{siape}")
    public ResponseEntity<ResponsavelResponse> atualizar(@PathVariable String siape, @Valid @RequestBody UpdateResponsavelRequest request){
        ResponsavelResponse response = service.atualizar(siape, request);
        return ResponseEntity.ok(response);
    }
}
