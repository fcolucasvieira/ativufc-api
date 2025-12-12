package br.ufc.ativufc.security.config;

import br.ufc.ativufc.security.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // se você usa @PreAuthorize
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            org.springframework.security.config.annotation.web.builders.HttpSecurity http,
            JwtAuthenticationFilter jwtAuthFilter
    ) throws Exception {

        return http
                // Desativa CSRF e session state
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // públicas
                        .requestMatchers("/auth/login", "/auth/requestReset", "/auth/confirmReset").permitAll()
                        .requestMatchers(HttpMethod.POST, "/discentes").permitAll()
                        .requestMatchers(HttpMethod.POST, "/cursos").permitAll()
                        .requestMatchers(HttpMethod.POST, "/instituicoes").permitAll()
                        .requestMatchers(HttpMethod.POST, "/subtipos").permitAll()

                        // protegidas por role DISCENTE
                        .requestMatchers("/discentes/**").hasRole("DISCENTE")
                        .requestMatchers(HttpMethod.GET, "/cursos/**").hasRole("DISCENTE")
                        .requestMatchers(HttpMethod.GET, "/instituicoes/**").hasRole("DISCENTE")
                        .requestMatchers("/solicitacoes/**").hasRole("DISCENTE")
                        .requestMatchers(HttpMethod.GET, "/subtipos/**").hasRole("DISCENTE")

                        // qualquer outra
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
