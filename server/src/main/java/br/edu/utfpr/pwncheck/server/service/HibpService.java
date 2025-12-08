package br.edu.utfpr.pwncheck.server.service;

import br.edu.utfpr.pwncheck.server.error.exception.TooManyRequest;
import br.edu.utfpr.pwncheck.server.model.dto.EmailDTO;
import io.github.cdimascio.dotenv.Dotenv;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.web.header.Header;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.TooManyRequests;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
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
        String apiKey = dotenv.get("key");
        String userAgent = "UTFPR-PwnCheck-App";

        try {
            return webClient.get()
                    .uri("https://haveibeenpwned.com/api/v3/breachedaccount/{email}", email)
                    .header("hibp-api-key", apiKey)
                    .header("user-agent", userAgent)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError(), resp -> {
                        if (resp.statusCode().value() == 404) {
                            // 404 → Nenhum vazamento
                            return Mono.error(new RuntimeException("NOT_FOUND"));
                        }
                        if (resp.statusCode().value() == 429) {
                            String retryAfter = resp.headers().asHttpHeaders().getFirst("Retry-After");
                            String msg = "Limite de requisições atingido (429)";
                            if (retryAfter != null) {
                                msg += ", tente novamente em " + retryAfter + " segundos";
                            }
                            return Mono.error(new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, msg));
                        }
                        return Mono.error(new RuntimeException("Erro 4xx ao consultar HIBP: " + resp.statusCode()));
                    })
                    .bodyToMono(new ParameterizedTypeReference<List<EmailDTO>>() {})
                    .onErrorResume(e -> {
                        // Se for 404, retornamos lista vazia
                        if (e.getMessage() != null && e.getMessage().contains("NOT_FOUND")) {
                            return Mono.just(Collections.emptyList());
                        }
                        // Outros erros propagam
                        return Mono.error(e);
                    })
                    .block();

        } catch (ResponseStatusException e) {
            // Aqui podemos tratar o 429 e lançar de novo
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao comunicar com a API do HIBP", e);
        }
    }


    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }
}
