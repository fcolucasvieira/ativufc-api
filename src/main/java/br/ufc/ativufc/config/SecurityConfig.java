package br.ufc.ativufc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/discentes").permitAll()
                        .requestMatchers(HttpMethod.POST, "/subtipo").permitAll()
                        .requestMatchers(HttpMethod.POST, "/instituicao").permitAll()
                        .requestMatchers(HttpMethod.POST, "/solicitacao").permitAll()
                        .anyRequest().authenticated()
                )
                .build();
    }

}
