package br.ufc.ativufc.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    private final long expirationMs = 1000 * 60 * 60;

    public String gerarToken(String email) {
        return Jwts.builder()
                .setSubject(email) // sub = dono do token
                .setIssuedAt(new Date()) // iat = data de criacao
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs)) // exp = data de expiracao
                .signWith(SignatureAlgorithm.HS256, secretKey) // algoritmo + chave secreta
                .compact();
    }

    public boolean tokenValido(String token) {
        return !isTokenExpirado(token);
    }

    public String extrairEmail(String token) {
        return extrairClaim(token, Claims::getSubject);
    }

    private boolean isTokenExpirado(String token) {
        Date expiration = extrairClaim(token, Claims::getExpiration);
        return expiration.before(new Date());
    }

    private <T> T extrairClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }
}
