package br.ufc.ativufc.service.jwt;

import br.ufc.ativufc.exception.OperationNotAllowedException;
import br.ufc.ativufc.model.Usuario; // ajuste conforme o nome da sua entidade de usuário
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtServiceLogin {
    private final JwtServiceBase base;
    private final Duration expiration;

    public JwtServiceLogin(JwtServiceBase base,
                           @Value("${security.jwt.login.ttl-minutes:120}") long ttlMinutes){
        this.base = base;
        this.expiration = Duration.ofMinutes(ttlMinutes);
    }

    public String gerarTokenLogin(Usuario usuario){
        Instant now = Instant.now();

        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .setIssuer(base.getIssuer())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(expiration)))
                .claim("perfil", usuario.getPerfil())
                .claim("tipo", "login")
                .claim(getIdentificadorKey(usuario.getPerfil().name()), getIdentificadorValue(usuario))
                .signWith(base.getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private String getIdentificadorKey(String perfil) {
        switch (perfil) {
            case "DISCENTE": return "matricula";
            case "RESPONSAVEL": return "siape";
            case "ADMIN": return "id";
            default: return "id";
        }
    }

    private Object getIdentificadorValue(Usuario usuario) {
        switch (usuario.getPerfil().name()) {
            case "DISCENTE": return usuario.getDiscente().getMatricula();
            case "RESPONSAVEL": return usuario.getResponsavel().getSiape();
            case "ADMIN": return usuario.getId();
            default: return usuario.getId();
        }
    }

    public String validarTokenLogin(String token){
        Claims claims = base.parseClaims(token);

        if(!"login".equals(claims.get("tipo")))
            throw new OperationNotAllowedException("Token inválido para login");

        if(claims.getExpiration().before(new Date()))
            throw new OperationNotAllowedException("Token expirado");

        return claims.getSubject();
    }
}
