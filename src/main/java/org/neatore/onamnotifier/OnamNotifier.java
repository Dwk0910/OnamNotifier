package org.neatore.onamnotifier;

import lombok.RequiredArgsConstructor;

import org.neatore.onamnotifier.service.TokenService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableJpaAuditing
public class OnamNotifier {
    public static void main(String[] args) {
        SpringApplication.run(OnamNotifier.class, args);
    }
}

@RestController
@RequiredArgsConstructor
class Controller {
    private final TokenService tokenService;

    @GetMapping("/me")
    public ResponseEntity<String> me(@CookieValue("ONN_ACCESS") String token) {
        return ResponseEntity.ok(
                this.tokenService.getUserEmail(token)
        );
    }
}
