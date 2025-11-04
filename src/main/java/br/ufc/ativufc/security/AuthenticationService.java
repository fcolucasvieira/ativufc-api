package br.ufc.ativufc.security;


import br.ufc.ativufc.model.Usuario;
import br.ufc.ativufc.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UsuarioRepository repository;

    // Serviço responsável por gerar de token JWT
    private final JwtService jwtService;

    // Gerenciador de autenticação do Spring Security
    private final AuthenticationManager authenticationManager;

    // Codificador de senha (usado para validar login)
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UsuarioRepository repository, JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public String autenticar(String email, String senha) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, senha)
            );

            Usuario usuario = repository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            return jwtService.gerarToken(usuario.getEmail());
        } catch (AuthenticationException e) {
            throw new RuntimeException("Credenciais inválidas");
        }
    }
}
