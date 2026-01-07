package br.ufc.ativufc.service;

import br.ufc.ativufc.dto.request.update.UpdateDiscenteRequest;
import br.ufc.ativufc.dto.request.DiscenteRequest;
import br.ufc.ativufc.dto.response.DiscenteResponse;
import br.ufc.ativufc.exception.NotFoundException;
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
                request.telefone(),
                Perfil.DISCENTE,
                true,
                null,
                null
        );

        Discente discente = new Discente(
                request.matricula(),
                request.ingressao(),
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
    public void remover(String matricula){
        Discente discente = discenteRepository.findByMatricula(matricula)
                .orElseThrow(() -> new NotFoundException("Discente não encontrado"));

        discenteRepository.delete(discente);
    }

    public DiscenteResponse toResponse(Discente discente) {
        return new DiscenteResponse(
                discente.getMatricula(),
                discente.getUsuario().getNome(),
                discente.getUsuario().getEmail(),
                discente.getUsuario().getTelefone(),
                discente.getIngressao(),
                discente.getCurso().getNome(),
                discente.getCurso().getTotalHorasComplementares(),
                discente.getHorasCumpridas(),
                discente.getHorasRestantes()
        );
    }
}
