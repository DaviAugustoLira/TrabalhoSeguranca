package br.edu.utfpr.pwncheck.server.controller;
import br.edu.utfpr.pwncheck.server.model.dto.EmailDTO;
import br.edu.utfpr.pwncheck.server.service.HibpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor

@RestController
@RequestMapping("/api")
public class CheckController {

    private final HibpService hibpService;

    @PostMapping("/check")
    public Map<String, Object> checkPassword(@RequestBody Map<String, String> body) {
        String password = body.get("password");

        boolean pwned = hibpService.isPasswordPwned(password);

        return Map.of(
                "pwned", pwned,
                "message", pwned ?
                        "Senha encontrada em vazamentos!" :
                        "Senha não encontrada em vazamentos conhecidos."
        );
    }


    @PostMapping("/email")
    public Map<String, Object> checkEmail(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        List<EmailDTO> result = hibpService.isEmailPwned(email);

        boolean pwned = !result.isEmpty()   ;

        return Map.of(
                "email", email,
                "pwned", pwned,
                "result", result
        );
    }


}
