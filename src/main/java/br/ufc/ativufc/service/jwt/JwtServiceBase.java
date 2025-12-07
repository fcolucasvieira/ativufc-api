package br.ufc.ativufc.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@Getter
public class JwtServiceBase {
    private final SecretKey secretKey;
    private final String issuer;

    public JwtServiceBase(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.issuer:ativufc}") String issuer) {

        if (secret == null || secret.trim().length() < 32)
            throw new IllegalArgumentException("JWT secret deve ter pelo menos 32 caracteres");

        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.issuer = issuer;
    }

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

    public String extrairEmail(String token){
        return parseClaims(token).getSubject();
    }

    public String extrairPerfil(String token){
        return parseClaims(token).get("perfil", String.class);
    }
}
