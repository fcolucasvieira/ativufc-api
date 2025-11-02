package br.ufc.ativufc.controller;

import br.ufc.ativufc.dto.UsuarioRequest;
import br.ufc.ativufc.dto.UsuarioResponse;
import br.ufc.ativufc.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> cadastrar(@Valid @RequestBody UsuarioRequest request){
        UsuarioResponse response = service.cadastrar(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id){
        UsuarioResponse response = service.buscarPorId(id);

        if (response == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listarTodos(){
        List<UsuarioResponse> lista = service.listarTodos();

        return ResponseEntity.ok(lista);
    }


}
