package br.ufc.ativufc.service;

import br.ufc.ativufc.dto.request.DiscenteRequest;
import br.ufc.ativufc.dto.response.DiscenteResponse;
import br.ufc.ativufc.exception.AlreadyExistsException;
import br.ufc.ativufc.exception.NotFoundException;
import br.ufc.ativufc.exception.OperationNotAllowedException;
import br.ufc.ativufc.model.Curso;
import br.ufc.ativufc.model.Discente;
import br.ufc.ativufc.model.Perfil;
import br.ufc.ativufc.model.Usuario;
import br.ufc.ativufc.repository.CursoRepository;
import br.ufc.ativufc.repository.DiscenteRepository;
import br.ufc.ativufc.repository.UsuarioRepository;
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

    // Cadastrar discente + usuário com validação
    public DiscenteResponse cadastrar(DiscenteRequest request) {
        if (discenteRepository.existsByMatricula(request.matricula()))
            throw new AlreadyExistsException("Discente já cadastrado com esta matrícula");

        Curso curso = cursoRepository.findById(request.idCurso())
                .orElseThrow(() -> new NotFoundException("Curso não encontrado"));

        if(request.horasCumpridas() != null && request.horasCumpridas() > curso.getTotalHorasComplementares())
            throw new OperationNotAllowedException("Horas cumpridas iniciais não podem exceder o total de horas complementares do curso");

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
        List<Discente> lista = discenteRepository.findAll();

        if(lista.isEmpty())
            throw new NotFoundException("Nenhum discente cadastrado");

        return lista.stream().map(this::toResponse).toList();
    }

    public DiscenteResponse atualizar(String matricula, DiscenteRequest request) {
        Discente discente = discenteRepository.findByMatricula(matricula)
                .orElseThrow(() -> new NotFoundException("Discente não encontrado"));

        if(!discente.getMatricula().equals(request.matricula()))
            throw new OperationNotAllowedException("Não é permitido alterar a matrícula do discente");

        if(!discente.getHorasCumpridas().equals(request.horasCumpridas()))
            throw new OperationNotAllowedException("Não é permitido alterar a quantidade de horas cumpridas uma vez cadastrado");

        Curso curso = cursoRepository.findById(request.idCurso())
                        .orElseThrow(() -> new NotFoundException("Curso não encontrado"));

        discente.setNome(request.nome());
        discente.setIngressao(request.ingressao());
        discente.setCurso(curso);

        discenteRepository.save(discente);
        return toResponse(discente);
    }

    public void remover(String matricula){
        Discente discente = discenteRepository.findByMatricula(matricula)
                .orElseThrow(() -> new NotFoundException("Discente não encontrado"));

        discenteRepository.delete(discente);
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
                discente.getHorasRestantes()
        );
    }
}
