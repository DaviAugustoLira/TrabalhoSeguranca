package br.edu.utfpr.pwncheck.server.service;

import br.edu.utfpr.pwncheck.server.model.dto.EmailDTO;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.web.header.Header;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor

@Service
public class HibpService {

    private final WebClient webClient;

    private final Dotenv dotenv;

    public boolean isPasswordPwned(String password) {
        try {
            // Gera SHA-1 da senha
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(password.getBytes(StandardCharsets.UTF_8));
            String hash = bytesToHex(digest).toUpperCase();

            String prefix = hash.substring(0, 5);
            String suffix = hash.substring(5);

            // Chama API
            String response = webClient.get()
                    .uri("https://api.pwnedpasswords.com/range/"+prefix)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // Verifica se o hash completo está na resposta
            if (response != null) {
                for (String line : response.split("\n")) {
                    if (line.startsWith(suffix)) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar Have I Been Pwned", e);
        }
    }

    public List<EmailDTO> isEmailPwned(String email) {
        try {
            String apiKey = dotenv.get("key");
            String userAgent = "UTFPR-PwnCheck-App";

            return webClient.get()
                    .uri("https://haveibeenpwned.com/api/v3/breachedaccount/{email}", email)
                    .header("hibp-api-key", apiKey)
                    .header("user-agent", userAgent)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<EmailDTO>>() {})
                    .onErrorResume(e -> {
                        System.out.println("Erro ao consultar HIBP: " + e.getMessage());
                        return Mono.just(Collections.emptyList());
                    })
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao comunicar com a API do HIPB");
        }
    }


    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }
}
