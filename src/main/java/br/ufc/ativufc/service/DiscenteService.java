package br.ufc.ativufc.service;

import br.ufc.ativufc.dto.request.DiscenteRequest;
import br.ufc.ativufc.dto.response.DiscenteResponse;
import br.ufc.ativufc.model.Discente;
import br.ufc.ativufc.model.Perfil;
import br.ufc.ativufc.model.Usuario;
import br.ufc.ativufc.repository.DiscenteRepository;
import br.ufc.ativufc.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public DiscenteResponse cadastrar(DiscenteRequest request) {
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
        Discente discente = discenteRepository.findByMatricula(matricula).get();
        return toResponse(discente);
    }

    public List<DiscenteResponse> listarTodos() {
        return discenteRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public DiscenteResponse atualizar(String matricula, DiscenteRequest request) {
        Discente discente = discenteRepository.findByMatricula(matricula).get();

        discente.setNome(request.nome());
        discente.setIngressao(request.ingressao());
        discente.setTotalHorasComplementares(request.totalHorasComplementares());

        discenteRepository.save(discente);

        return toResponse(discente);
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
