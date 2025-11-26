package br.ufc.ativufc.service;

import br.ufc.ativufc.dto.request.UsuarioRequest;
import br.ufc.ativufc.dto.response.UsuarioResponse;
import br.ufc.ativufc.model.Usuario;
import br.ufc.ativufc.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponse cadastrar(UsuarioRequest request) {
        Usuario usuario = new Usuario(
                null,
                request.nome(),
                request.email(),
                passwordEncoder.encode(request.senha()),
                request.perfil(),
                true,
                null,
                null
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

    public UsuarioResponse atualizarAtivo(Long id, boolean ativo) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setAtivo(ativo);
        repository.save(usuario);

        return toResponse(usuario);
    }

    public void remover(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        repository.delete(usuario);
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
