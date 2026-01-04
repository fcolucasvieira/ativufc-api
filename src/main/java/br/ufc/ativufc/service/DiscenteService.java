package br.ufc.ativufc.service;

import br.ufc.ativufc.dto.request.AlterarSenhaDTO;
import br.ufc.ativufc.dto.request.DiscenteUpdateDTO;
import br.ufc.ativufc.dto.request.update.UpdateDiscenteRequest;
import br.ufc.ativufc.dto.request.DiscenteRequest;
import br.ufc.ativufc.dto.response.DiscenteResponse;
import br.ufc.ativufc.exception.AlreadyExistsException;
import br.ufc.ativufc.exception.NotFoundException;
import br.ufc.ativufc.exception.OperationNotAllowedException;
import br.ufc.ativufc.model.Curso;
import br.ufc.ativufc.model.Discente;
import br.ufc.ativufc.model.enums.Perfil;
import br.ufc.ativufc.model.Usuario;
import br.ufc.ativufc.repository.CursoRepository;
import br.ufc.ativufc.repository.DiscenteRepository;
import br.ufc.ativufc.repository.UsuarioRepository;
import br.ufc.ativufc.utils.validation.CommonValidation;
import br.ufc.ativufc.utils.validation.CursoValidation;
import br.ufc.ativufc.utils.validation.DiscenteValidation;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscenteService {

    private final DiscenteRepository discenteRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;
    private final PasswordEncoder passwordEncoder;

    public DiscenteService(DiscenteRepository discenteRepository, UsuarioRepository usuarioRepository, CursoRepository cursoRepository, PasswordEncoder passwordEncoder) {
        this.discenteRepository = discenteRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public DiscenteResponse cadastrar(DiscenteRequest request) {
        DiscenteValidation.validarMatriculaUnica(discenteRepository, request.matricula());

        Curso curso = cursoRepository.findById(request.idCurso())
                .orElseThrow(() -> new NotFoundException("Curso não encontrado"));
        CursoValidation.validarHorasCumpridas(curso, request.horasCumpridas());

        CommonValidation.validarEmailUnico(usuarioRepository, request.email());
        CommonValidation.validarSenhaForte(request.senha());

        Usuario usuario = new Usuario(
                null,
                request.nome(),
                request.email(),
                passwordEncoder.encode(request.senha()),
                Perfil.DISCENTE,
                true,
                null,
                null
        );

        Discente discente = new Discente(
                request.matricula(),
                request.nome(),
                request.ingressao(),
                null,
                curso,
                request.horasCumpridas(),
                usuario
        );

        discenteRepository.save(discente);
        return toResponse(discente);
    }


    public DiscenteResponse buscarPorMatricula(String matricula) {
        Discente discente = discenteRepository.findByMatricula(matricula)
                .orElseThrow(() -> new NotFoundException("Discente não encontrado"));
        return toResponse(discente);
    }

    public List<DiscenteResponse> listarTodos() {
        return discenteRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public DiscenteResponse atualizar(String matricula, UpdateDiscenteRequest request) {
        Discente discente = discenteRepository.findByMatricula(matricula)
                .orElseThrow(() -> new NotFoundException("Discente não encontrado"));

        if (request.nome() != null && !request.nome().isBlank()) {
            discente.setNome(request.nome());
            discente.getUsuario().setNome(request.nome());
        }

        if (request.ingressao() != null) {
            discente.setIngressao(request.ingressao());
        }

        if (request.idCurso() != null) {
            Curso curso = cursoRepository.findById(request.idCurso())
                    .orElseThrow(() -> new NotFoundException("Curso não encontrado"));
            discente.setCurso(curso);
        }

        discenteRepository.save(discente);
        return toResponse(discente);
    }


    @Transactional
    public void remover(String matricula){
        Discente discente = discenteRepository.findByMatricula(matricula)
                .orElseThrow(() -> new NotFoundException("Discente não encontrado"));

        discenteRepository.delete(discente);
    }

    @Transactional
    public DiscenteResponse atualizarPerfil(String matricula, DiscenteUpdateDTO dados) {
        Discente discente = discenteRepository.findByMatricula(matricula)
                .orElseThrow(() -> new NotFoundException("Discente não encontrado"));

        Usuario usuario = discente.getUsuario(); // Pega o usuário vinculado (login)

        // atualiza nome em ambos
        if (dados.nome() != null && !dados.nome().isBlank()) {
            discente.setNome(dados.nome());
            usuario.setNome(dados.nome());
        }

        // atualiza o email, verifica se já existe outro usuário com esse email
        if (dados.email() != null && !dados.email().isBlank()) {
            if (!dados.email().equals(usuario.getEmail())) {
                CommonValidation.validarEmailUnico(usuarioRepository, dados.email());
                usuario.setEmail(dados.email());
            }
        }

        // atualiza telefone
        if (dados.telefone() != null) {
            discente.setTelefone(dados.telefone());
        }

        // salva tudo
        usuarioRepository.save(usuario);
        discenteRepository.save(discente);

        return toResponse(discente);
    }

    @Transactional
    public void alterarSenha(String matricula, AlterarSenhaDTO dados) {
        Discente discente = discenteRepository.findByMatricula(matricula)
                .orElseThrow(() -> new NotFoundException("Discente não encontrado"));

        Usuario usuario = discente.getUsuario();

        // verifica a senha antiga no objeto USUARIO
        if (!passwordEncoder.matches(dados.senhaAtual(), usuario.getSenha())) {
            throw new OperationNotAllowedException("A senha atual está incorreta.");
        }

        // Valida força da nova senha e salva
        CommonValidation.validarSenhaForte(dados.novaSenha());
        usuario.setSenha(passwordEncoder.encode(dados.novaSenha()));

        usuarioRepository.save(usuario);
    }

    public DiscenteResponse toResponse(Discente discente) {
        return new DiscenteResponse(
                discente.getMatricula(),
                discente.getNome(),
                discente.getUsuario().getEmail(),
                discente.getIngressao(),
                discente.getCurso().getNome(),
                discente.getCurso().getTotalHorasComplementares(),
                discente.getHorasCumpridas(),
                discente.getHorasRestantes(),
                discente.getTelefone()
        );
    }
}
