package br.ufc.ativufc.controller;

import br.ufc.ativufc.dto.response.ComprovanteResponse;
import br.ufc.ativufc.service.ComprovanteService;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/comprovantes")
public class ComprovanteController {
    private final ComprovanteService service;

    public ComprovanteController(ComprovanteService service){
        this.service = service;
    }

    @PreAuthorize("hasRole('DISCENTE')")
    @PostMapping("/{solicitacaoId}")
    public ResponseEntity<ComprovanteResponse> upload(@PathVariable Long solicitacaoId, @RequestParam("file")MultipartFile file){
        ComprovanteResponse response = service.upload(solicitacaoId, file);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("(hasRole('DISCENTE') and @securityUtil.isComprovanteOwner(#id)) or hasRole('RESPONSAVEL')")
    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> download(@PathVariable Long id) {
        ComprovanteResponse response = service.buscarPorId(id);
        PathResource resource = service.carregarArquivo(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(response.contentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + response.nomeOriginal() + "\"")
                .body(resource);
    }

    @PreAuthorize("hasRole('RESPONSAVEL') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ComprovanteResponse> buscarPorId(@PathVariable Long id){
        ComprovanteResponse response = service.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('RESPONSAVEL') or hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ComprovanteResponse>> listarTodos(){
        List<ComprovanteResponse> lista = service.listarTodos();
        return ResponseEntity.ok(lista);
    }

    @PreAuthorize("hasRole('DISCENTE') and @securityUtil.isSolicitacaoOwner(#solicitacaoId)")
    @PutMapping("/{solicitacaoId}")
    public ResponseEntity<ComprovanteResponse> atualizar(@PathVariable Long solicitacaoId, @RequestParam("file") MultipartFile file){
        ComprovanteResponse response = service.atualizar(solicitacaoId, file);
        return ResponseEntity.ok(response);
    }
}
