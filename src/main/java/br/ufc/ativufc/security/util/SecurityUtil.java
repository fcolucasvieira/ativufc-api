package br.ufc.ativufc.security.util;

import br.ufc.ativufc.exception.NotFoundException;
import br.ufc.ativufc.model.Solicitacao;
import br.ufc.ativufc.repository.SolicitacaoRepository;
import br.ufc.ativufc.service.jwt.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("securityUtil")
public class SecurityUtil {
    private final JwtService jwtService;
    private final SolicitacaoRepository solicitacaoRepository;

    public SecurityUtil(JwtService jwtService, SolicitacaoRepository solicitacaoRepository) {
        this.jwtService = jwtService;
        this.solicitacaoRepository = solicitacaoRepository;
    }

    public boolean isDiscenteOwner(String matricula) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated()) return false;

        String token = (String) auth.getCredentials();
        Claims claims = jwtService.parseClaims(token);
        String matriculaToken = claims.get("matricula", String.class);

        return matriculaToken != null && matriculaToken.equals(matricula);
    }

    public boolean isResponsavelOwner(String siape) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return false;

        String token = (String) auth.getCredentials();
        Claims claims = jwtService.parseClaims(token);
        String siapeToken = claims.get("siape", String.class);

        return siapeToken != null && siapeToken.equals(siape);
    }

        public boolean isSolicitacaoOwner(Long id) {
            Solicitacao solicitacao = solicitacaoRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Solicitação não encontrada"));
            String matriculaSolicitacao = solicitacao.getDiscente().getMatricula();
            return isDiscenteOwner(matriculaSolicitacao);
        }
    }
