package br.edu.utfpr.pwncheck.server.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())                    // desabilita CSRF (se API stateless)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()                    // permite todas as requisições
                )
                .httpBasic(Customizer.withDefaults());           // opcional: pode remover se não quiser basic auth

        // Se quiser desabilitar completamente httpBasic e formLogin:
        // .httpBasic(AbstractHttpConfigurer::disable)
        // .formLogin(AbstractHttpConfigurer::disable)

        return http.build();
    }
}
