package br.ufc.ativufc.service.jwt;

import br.ufc.ativufc.exception.OperationNotAllowedException;
import br.ufc.ativufc.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey secretKey;
    private final String issuer;
    private final Duration loginExpiration;
    private final Duration resetExpiration;

    public JwtService(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.issuer:ativufc}") String issuer,
            @Value("${security.jwt.login.ttl-minutes:120}") long loginTtlMinutes,
            @Value("${security.jwt.reset.ttl-minutes:15}") long resetTtlMinutes) {

        if (secret == null || secret.trim().length() < 32)
            throw new IllegalArgumentException("JWT secret deve ter pelo menos 32 caracteres");

        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.issuer = issuer;
        this.loginExpiration = Duration.ofMinutes(loginTtlMinutes);
        this.resetExpiration = Duration.ofMinutes(resetTtlMinutes);
    }

    // LOGIN
    public String gerarTokenLogin(Usuario usuario) {
        Instant now = Instant.now();

        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .setIssuer(issuer)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(loginExpiration)))
                .claim("perfil", usuario.getPerfil().name())
                .claim("tipo", "login")
                .claim("id", usuario.getId())
                .claim(getIdentificadorKey(usuario.getPerfil().name()), getIdentificadorValue(usuario))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public void validarTokenLogin(String token) {
        Claims claims = parseClaims(token);

        if (!"login".equals(claims.get("tipo")))
            throw new OperationNotAllowedException("Token inválido para login");

        if (claims.getExpiration().before(new Date()))
            throw new OperationNotAllowedException("Token expirado");
    }

    // RESET
    public String gerarTokenReset(String email) {
        Instant now = Instant.now();

        return Jwts.builder()
                .setSubject(email)
                .setIssuer(issuer)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(resetExpiration)))
                .claim("tipo", "reset")
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String validarTokenReset(String token) {
        Claims claims = parseClaims(token);

        if (!"reset".equals(claims.get("tipo")))
            throw new OperationNotAllowedException("Token inválido para reset de senha");

        if (claims.getExpiration().before(new Date()))
            throw new OperationNotAllowedException("Token de reset expirado");

        return claims.getSubject();
    }

    // UTIL
    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValido(String token) {
        try {
            Claims claims = parseClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String extrairEmail(String token) {
        return parseClaims(token).getSubject();
    }

    public String extrairPerfil(String token) {
        return parseClaims(token).get("perfil", String.class);
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
}
