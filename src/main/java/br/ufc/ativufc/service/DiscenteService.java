package br.ufc.ativufc.service;

import br.ufc.ativufc.dto.request.DiscenteRequest;
import br.ufc.ativufc.dto.response.DiscenteResponse;
import br.ufc.ativufc.exception.DiscenteAlreadyExistsException;
import br.ufc.ativufc.exception.DiscenteNotFoundException;
import br.ufc.ativufc.model.Discente;
import br.ufc.ativufc.model.Perfil;
import br.ufc.ativufc.model.Usuario;
import br.ufc.ativufc.repository.DiscenteRepository;
import br.ufc.ativufc.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscenteService {

    private final DiscenteRepository discenteRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DiscenteService(DiscenteRepository discenteRepository, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.discenteRepository = discenteRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Cadastrar discente + usuário com validação
    public DiscenteResponse cadastrar(DiscenteRequest request) {
        if (discenteRepository.existsByMatricula(request.matricula()))
            throw new DiscenteAlreadyExistsException();

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
                request.totalHorasComplementares(),
                usuario
        );

        discenteRepository.save(discente);
        return toResponse(discente);
    }


    public DiscenteResponse buscarPorMatricula(String matricula) {
        Discente discente = discenteRepository.findByMatricula(matricula)
                .orElseThrow(() -> new DiscenteNotFoundException("Discente não encontrado"));
        return toResponse(discente);
    }

    public List<DiscenteResponse> listarTodos() {
        List<Discente> lista = discenteRepository.findAll();

        if(lista.isEmpty())
            throw new DiscenteNotFoundException("Nenhum discente cadastrado");

        return lista.stream().map(this::toResponse).toList();
    }

    public DiscenteResponse atualizar(String matricula, DiscenteRequest request) {
        Discente discente = discenteRepository.findByMatricula(matricula)
                .orElseThrow(() -> new DiscenteNotFoundException("Discente não encontrado"));

        if(!discente.getMatricula().equals(request.matricula()))
            throw new RuntimeException("Não é permitido alterar a matrícula do discente");

        discente.setNome(request.nome());
        discente.setIngressao(request.ingressao());
        discente.setTotalHorasComplementares(request.totalHorasComplementares());


        discenteRepository.save(discente);
        return toResponse(discente);
    }

    public void deletar(String matricula){
        Discente discente = discenteRepository.findByMatricula(matricula)
                .orElseThrow(() -> new DiscenteNotFoundException("Discente não encontrado"));

        discenteRepository.delete(discente);
    }

    public DiscenteResponse toResponse(Discente discente) {
        return new DiscenteResponse(
                discente.getMatricula(),
                discente.getNome(),
                discente.getUsuario().getEmail(),
                discente.getIngressao(),
                discente.getTotalHorasComplementares()
        );
    }
}
