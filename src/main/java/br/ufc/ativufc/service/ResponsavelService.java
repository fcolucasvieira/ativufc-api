package br.ufc.ativufc.service;

import br.ufc.ativufc.dto.request.ResponsavelRequest;
import br.ufc.ativufc.dto.response.ResponsavelResponse;
import br.ufc.ativufc.model.Perfil;
import br.ufc.ativufc.model.Responsavel;
import br.ufc.ativufc.model.Usuario;
import br.ufc.ativufc.repository.ResponsavelRepository;
import br.ufc.ativufc.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponsavelService {

    private final ResponsavelRepository responsavelRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponsavelService(ResponsavelRepository responsavelRepository, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.responsavelRepository = responsavelRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponsavelResponse cadastrar(ResponsavelRequest request) {
        Usuario usuario = new Usuario(
                null,
                request.nome(),
                request.email(),
                passwordEncoder.encode(request.senha()),
                Perfil.RESPONSAVEL,
                false, // inativo inicialmente
                null,
                null
        );

        Responsavel responsavel = new Responsavel(
                request.cpf(),
                request.nome(),
                usuario
        );

        responsavelRepository.save(responsavel);

        return toResponse(responsavel);
    }

    public ResponsavelResponse buscarPorCpf(String cpf){
        Responsavel responsavel = responsavelRepository.findByCpf(cpf).get();
        return toResponse(responsavel);
    }

    public List<ResponsavelResponse> listarTodos(){
        return responsavelRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public ResponsavelResponse atualizar(String cpf, ResponsavelRequest request){
        Responsavel responsavel = responsavelRepository.findByCpf(cpf).get();

        responsavel.setNome(request.nome());

        responsavelRepository.save(responsavel);

        return toResponse(responsavel);
    }

    public ResponsavelResponse toResponse(Responsavel responsavel) {
        return new ResponsavelResponse(
                responsavel.getCpf(),
                responsavel.getNome(),
                responsavel.getUsuario().getEmail(),
                responsavel.getUsuario().isAtivo()
        );
    }
}
