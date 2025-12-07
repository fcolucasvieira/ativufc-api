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
public class JwtServiceReset {

    private final JwtServiceBase base;
    private final Duration expiration;

    public JwtServiceReset(JwtServiceBase base,
                           @Value("${security.jwt.reset.ttl-minutes:15}") long ttlMinutes) {
        this.base = base;
        this.expiration = Duration.ofMinutes(ttlMinutes);
    }

    public String gerarTokenReset(String email) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(email)
                .setIssuer(base.getIssuer())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(expiration)))
                .claim("tipo", "reset")
                .signWith(base.getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String validarTokenReset(String token) {
        Claims claims = base.parseClaims(token);

        if (!"reset".equals(claims.get("tipo")))
            throw new OperationNotAllowedException("Token inválido para reset de senha");

        if (claims.getExpiration().before(new Date()))
            throw new OperationNotAllowedException("Token de reset expirado");

        return claims.getSubject();
    }
}
