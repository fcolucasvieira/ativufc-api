package br.ufc.ativufc.service.auth;

import br.ufc.ativufc.dto.response.auth.ResetSenhaResponse;
import br.ufc.ativufc.dto.response.jwt.TokenResponse;
import br.ufc.ativufc.exception.NotFoundException;
import br.ufc.ativufc.exception.OperationNotAllowedException;
import br.ufc.ativufc.model.Usuario;
import br.ufc.ativufc.repository.UsuarioRepository;
import br.ufc.ativufc.service.jwt.JwtService;
import br.ufc.ativufc.utils.validation.PasswordValidation;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    private final UsuarioRepository repository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UsuarioRepository repository,
                                 JwtService jwtService,
                                 PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public TokenResponse autenticar(String email, String senha) {
        Usuario usuario = repository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Usuário não encontrado"));

        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new BadCredentialsException("Credenciais inválidas");
        }

        String token = jwtService.gerarTokenLogin(usuario);

        Object identificador;
        switch (usuario.getPerfil().name()) {
            case "DISCENTE":
                identificador = usuario.getDiscente().getMatricula();
                break;
            case "RESPONSAVEL":
                identificador = usuario.getResponsavel().getSiape();
                break;
            case "ADMIN":
                identificador = usuario.getId();
                break;
            default:
                identificador = usuario.getId();
        }

        return new TokenResponse(token, usuario.getEmail(), usuario.getPerfil().name(), usuario.getId(), identificador);
    }

    public String iniciarResetSenha(String email) {
        Usuario usuario = repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        return jwtService.gerarTokenReset(usuario.getEmail());
    }

    public ResetSenhaResponse concluirResetSenha(String token, String novaSenha) {
        String email = jwtService.validarTokenReset(token);
        Usuario usuario = repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        List<String> falhas = PasswordValidation.validarSenha(novaSenha);
        if (!falhas.isEmpty()) {
            String mensagem = "Senha inválida:\n" + falhas.stream()
                    .map(f -> "- " + f)
                    .collect(Collectors.joining("\n"));
            throw new OperationNotAllowedException(mensagem);
        }

        usuario.setSenha(passwordEncoder.encode(novaSenha));
        repository.save(usuario);

        return new ResetSenhaResponse(true, "Senha redefinida com sucesso!");
    }
}
