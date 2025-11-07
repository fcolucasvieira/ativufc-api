package br.ufc.ativufc.service;

import br.ufc.ativufc.dto.UsuarioRequest;
import br.ufc.ativufc.dto.UsuarioResponse;
import br.ufc.ativufc.model.Usuario;
import br.ufc.ativufc.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public UsuarioResponse cadastrar(UsuarioRequest request) {
        Usuario usuario = new Usuario(
                null,
                request.nome(),
                request.email(),
                request.senha(),
                request.perfil(),
                true
        );

        repository.save(usuario);
        return toResponse(usuario);
    }

    public UsuarioResponse buscarPorId(Long id) {
        return repository.findById(id)
                .map(this::toResponse)
                .orElse(null);
    }

    public List<UsuarioResponse> listarTodos() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfil(),
                usuario.isAtivo()
        );
    }
}
