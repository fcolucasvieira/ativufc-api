package br.ufc.ativufc.service;

import br.ufc.ativufc.dto.request.UsuarioRequest;
import br.ufc.ativufc.dto.request.update.UpdateUsuarioRequest;
import br.ufc.ativufc.dto.response.UsuarioResponse;
import br.ufc.ativufc.exception.NotFoundException;
import br.ufc.ativufc.model.enums.Perfil;
import br.ufc.ativufc.model.Usuario;
import br.ufc.ativufc.repository.UsuarioRepository;
import br.ufc.ativufc.utils.validation.CommonValidation;
import br.ufc.ativufc.utils.validation.UsuarioValidation;
import jakarta.transaction.Transactional;
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

    // Cadastro de ADMIN
    @Transactional
    public UsuarioResponse cadastrarAdmin(UsuarioRequest request) {
        UsuarioValidation.validarPerfilAdmin(request.perfil());

        CommonValidation.validarEmailUnico(repository, request.email());
        CommonValidation.validarSenhaForte(request.senha());

        Usuario usuario = new Usuario(
                null,
                request.nome(),
                request.email(),
                passwordEncoder.encode(request.senha()),
                request.telefone(),
                request.perfil(),
                true,
                null,
                null
        );

        repository.save(usuario);
        return toResponse(usuario);
    }

    public UsuarioResponse buscarPorId(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        return toResponse(usuario);
    }

    public UsuarioResponse buscarPorEmail(String email){
        Usuario usuario = repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        return toResponse(usuario);
    }

    public List<UsuarioResponse> listarTodos() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public List<UsuarioResponse> listarPorAtivo(boolean ativo){
        return repository.findByAtivo(ativo).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public UsuarioResponse atualizar(Long id, UpdateUsuarioRequest request) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        if (request.nome() != null && !request.nome().isBlank()) {
            usuario.setNome(request.nome());
        }

        if (request.email() != null && !request.email().isBlank()) {
            CommonValidation.validarEmailUnico(repository, request.email());
            usuario.setEmail(request.email());
        }

        if (request.telefone() != null && !request.telefone().isBlank()) {
            usuario.setTelefone(request.telefone());
        }

        repository.save(usuario);
        return toResponse(usuario);
    }


    @Transactional
    public UsuarioResponse atualizarAtivo(Long id, boolean ativo) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        usuario.setAtivo(ativo);

        repository.save(usuario);
        return toResponse(usuario);
    }

    @Transactional
    public UsuarioResponse atualizarPerfil(Long id, Perfil perfil){
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        usuario.setPerfil(perfil);

        repository.save(usuario);
        return toResponse(usuario);
    }

    @Transactional
    public void remover(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        repository.delete(usuario);
    }

    public UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getPerfil(),
                usuario.isAtivo()
        );
    }
}
