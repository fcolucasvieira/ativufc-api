package br.ufc.ativufc.security.util;

import br.ufc.ativufc.service.jwt.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("securityUtil")
public class SecurityUtil {
    private final JwtService jwtService;

    public SecurityUtil(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public boolean isDiscenteOwner(String matricula) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated()) return false;

        String token = (String) auth.getCredentials();
        Claims claims = jwtService.parseClaims(token);
        String matriculaToken = claims.get("matricula", String.class);

        return matriculaToken != null && matriculaToken.equals(matricula);
    }
}
