package br.edu.utfpr.pwncheck.server.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

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

        http.cors(cors -> corsConfigurationSource());

        // Se quiser desabilitar completamente httpBasic e formLogin:
        // .httpBasic(AbstractHttpConfigurer::disable)
        // .formLogin(AbstractHttpConfigurer::disable)

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Lista das origens autorizadas, no nosso caso que iremos rodar a aplicação localmente o * poderia ser trocado
        // por: http://localhost:porta, em que :porta será a porta em que a aplicação cliente será executada
        configuration.setAllowedOrigins(List.of("*"));
        // Lista dos métodos HTTP autorizados
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT"));
        // Lista dos Headers autorizados, o Authorization será o header que iremos utilizar para transferir o Token
        configuration.setAllowedHeaders(List.of("Authorization","x-xsrf-token",
                "Access-Control-Allow-Headers", "Origin",
                "Accept", "X-Requested-With", "Content-Type",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers", "Auth-Id-Token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
