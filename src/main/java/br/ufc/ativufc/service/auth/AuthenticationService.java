package br.ufc.ativufc.service.auth;

import br.ufc.ativufc.dto.response.auth.ResetSenhaResponse;
import br.ufc.ativufc.dto.response.auth.TokenResponse;
import br.ufc.ativufc.exception.NotFoundException;
import br.ufc.ativufc.exception.OperationNotAllowedException;
import br.ufc.ativufc.model.Usuario;
import br.ufc.ativufc.repository.UsuarioRepository;
import br.ufc.ativufc.service.jwt.JwtServiceLogin;
import br.ufc.ativufc.service.jwt.JwtServiceReset;
import br.ufc.ativufc.utils.PasswordValidator;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    private final UsuarioRepository repository;
    private final JwtServiceLogin jwtLoginService;
    private final JwtServiceReset jwtResetService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UsuarioRepository repository,
                                 JwtServiceLogin jwtLoginService,
                                 JwtServiceReset jwtResetService,
                                 PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.jwtLoginService = jwtLoginService;
        this.jwtResetService = jwtResetService;
        this.passwordEncoder = passwordEncoder;
    }

    public TokenResponse autenticar(String email, String senha) {
        Usuario usuario = repository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Usuário não encontrado"));

        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new BadCredentialsException("Credenciais inválidas");
        }

        String token = jwtLoginService.gerarTokenLogin(usuario.getEmail(), usuario.getPerfil().name());
        return new TokenResponse(token, usuario.getEmail(), usuario.getPerfil().name());
    }

    public String iniciarResetSenha(String email) {
        Usuario usuario = repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        return jwtResetService.gerarTokenReset(usuario.getEmail());
    }

    public ResetSenhaResponse concluirResetSenha(String token, String novaSenha) {
        String email = jwtResetService.validarTokenReset(token);
        Usuario usuario = repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        List<String> falhas = PasswordValidator.validarSenha(novaSenha);
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
