package br.edu.utfpr.pwncheck.server.controller;
import br.edu.utfpr.pwncheck.server.service.HibpService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class CheckController {

    private final HibpService hibpService;

    public CheckController(HibpService hibpService) {
        this.hibpService = hibpService;
    }

    @PostMapping("/check")
    public Map<String, Object> checkPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        boolean pwned = hibpService.isPasswordPwned(password);

        return Map.of(
                "email", email,
                "pwned", pwned,
                "message", pwned ?
                        "⚠️ Senha encontrada em vazamentos!" :
                        "✅ Senha não encontrada em vazamentos conhecidos."
        );
    }

    @PostMapping("/email")
    public Map<String, Object> checkEmail(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String result = hibpService.isEmailPwned(email);

        boolean pwned = !result.startsWith("Erro") && !result.isBlank();

        return Map.of(
                "email", email,
                "pwned", pwned,
                "result", result
        );
    }


}
