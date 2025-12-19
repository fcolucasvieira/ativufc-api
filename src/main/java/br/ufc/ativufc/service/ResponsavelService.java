package br.ufc.ativufc.service;

import br.ufc.ativufc.dto.request.ResponsavelRequest;
import br.ufc.ativufc.dto.request.update.UpdateResponsavelRequest;
import br.ufc.ativufc.dto.response.ResponsavelResponse;
import br.ufc.ativufc.exception.NotFoundException;
import br.ufc.ativufc.model.Instituicao;
import br.ufc.ativufc.model.enums.Perfil;
import br.ufc.ativufc.model.Responsavel;
import br.ufc.ativufc.model.Usuario;
import br.ufc.ativufc.repository.InstituicaoRepository;
import br.ufc.ativufc.repository.ResponsavelRepository;
import br.ufc.ativufc.repository.UsuarioRepository;
import br.ufc.ativufc.utils.validation.CommonValidation;
import br.ufc.ativufc.utils.validation.ResponsavelValidation;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponsavelService {

    private final ResponsavelRepository responsavelRepository;
    private final InstituicaoRepository instituicaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponsavelService(ResponsavelRepository responsavelRepository, InstituicaoRepository instituicaoRepository, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.responsavelRepository = responsavelRepository;
        this.instituicaoRepository = instituicaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponsavelResponse cadastrar(ResponsavelRequest request) {
        ResponsavelValidation.validarSiapeUnico(responsavelRepository, request.siape());

        Instituicao instituicao = instituicaoRepository.findById(request.idInstituicao())
                        .orElseThrow(() -> new NotFoundException("Instituição não encontrada"));

        CommonValidation.validarEmailUnico(usuarioRepository, request.email());
        CommonValidation.validarSenhaForte(request.senha());

        Usuario usuario = new Usuario(
                null,
                request.nome(),
                request.email(),
                passwordEncoder.encode(request.senha()),
                Perfil.RESPONSAVEL,
                false, // inativo inicial (ADMIN ativa acesso)
                null,
                null
        );

        Responsavel responsavel = new Responsavel(
                request.siape(),
                request.nome(),
                instituicao,
                request.cargo(),
                usuario
        );

        responsavelRepository.save(responsavel);
        return toResponse(responsavel);
    }

    public ResponsavelResponse buscarPorSiape(String siape){
        Responsavel responsavel = responsavelRepository.findBySiape(siape)
                .orElseThrow(() -> new NotFoundException("Responsável não encontrado"));
        return toResponse(responsavel);
    }

    public List<ResponsavelResponse> listarTodos(){
        return responsavelRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public ResponsavelResponse atualizar(String siape, UpdateResponsavelRequest request){
        Responsavel responsavel = responsavelRepository.findBySiape(siape)
                .orElseThrow(() -> new NotFoundException("Responsável não encontrado"));

        if (request.nome() != null && !request.nome().isBlank()) {
            responsavel.setNome(request.nome());
        }

        if (request.idInstituicao() != null) {
            Instituicao instituicao = instituicaoRepository.findById(request.idInstituicao())
                    .orElseThrow(() -> new NotFoundException("Instituição não encontrada"));
            responsavel.setInstituicao(instituicao);
        }

        if(request.cargo() != null){
            responsavel.setCargo(request.cargo());
        }

        responsavelRepository.save(responsavel);
        return toResponse(responsavel);
    }

    public ResponsavelResponse toResponse(Responsavel responsavel) {
        return new ResponsavelResponse(
                responsavel.getSiape(),
                responsavel.getNome(),
                responsavel.getInstituicao().getNome(),
                responsavel.getCargo(),
                responsavel.getUsuario().getEmail(),
                responsavel.getUsuario().isAtivo()
        );
    }
}
