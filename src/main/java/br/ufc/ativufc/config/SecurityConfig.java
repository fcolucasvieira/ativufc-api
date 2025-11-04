package br.ufc.ativufc.config;

import br.ufc.ativufc.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable()) // ✅ forma correta no Spring Security moderno
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()

                        // 🔓 Libera acesso ao H2 Console
                        .requestMatchers("/h2-console/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/discentes").permitAll()
                        .requestMatchers(HttpMethod.GET, "/discentes").permitAll()
                        .requestMatchers(HttpMethod.GET, "/discentes/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/discentes/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/subtipos").permitAll()
                        .requestMatchers(HttpMethod.GET, "/subtipos").permitAll()
                        .requestMatchers(HttpMethod.GET, "/subtipos/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/instituicoes").permitAll()
                        .requestMatchers(HttpMethod.GET, "/instituicoes").permitAll()
                        .requestMatchers(HttpMethod.GET, "/instituicoes/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/solicitacoes").permitAll()
                        .requestMatchers(HttpMethod.GET, "/solicitacoes").permitAll()
                        .requestMatchers(HttpMethod.GET, "/solicitacoes/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/solicitacoes/discente/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/solicitacoes/status").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/solicitacoes/*/status").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/solicitacoes/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                        .requestMatchers(HttpMethod.GET, "/usuarios").permitAll()
                        .requestMatchers(HttpMethod.GET, "/usuarios/**").permitAll()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
