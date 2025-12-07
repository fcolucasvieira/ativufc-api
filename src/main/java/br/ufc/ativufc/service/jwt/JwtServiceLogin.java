package br.ufc.ativufc.service.jwt;

import br.ufc.ativufc.exception.OperationNotAllowedException;
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

    public String gerarTokenLogin(String email, String perfil){
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(email)
                .setIssuer(base.getIssuer())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(expiration)))
                .claim("perfil", perfil)
                .claim("tipo", "login")
                .signWith(base.getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
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
