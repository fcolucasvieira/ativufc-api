package br.ufc.ativufc.utils.security;

import br.ufc.ativufc.service.jwt.JwtServiceBase;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtServiceBase jwtService;

    public JwtAuthenticationFilter(JwtServiceBase jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        System.out.println("➡️ JwtAuthenticationFilter chamado para: " + request.getRequestURI());

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("❌ Nenhum header Authorization válido encontrado");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        System.out.println("🔑 Token recebido: " + token);

        boolean valido = jwtService.isTokenValido(token);
        System.out.println("✅ Token válido? " + valido);

        if (!valido) {
            System.out.println("❌ Token rejeitado pelo JwtServiceBase");
            filterChain.doFilter(request, response);
            return;
        }

        String email = jwtService.extrairEmail(token);
        String perfil = jwtService.extrairPerfil(token);

        System.out.println("📧 Email extraído: " + email);
        System.out.println("👤 Perfil extraído: " + perfil);

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            List<SimpleGrantedAuthority> authorities =
                    List.of(new SimpleGrantedAuthority("ROLE_" + perfil)); // casa com hasRole("DISCENTE")

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(email, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("✅ Autenticado: " + email + " com perfil: ROLE_" + perfil);
        } else {
            System.out.println("⚠️ Não foi possível autenticar: email nulo ou contexto já preenchido");
        }

        filterChain.doFilter(request, response);
    }
}
