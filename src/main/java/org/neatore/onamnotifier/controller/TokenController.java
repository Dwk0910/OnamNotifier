package org.neatore.onamnotifier.controller;

import lombok.RequiredArgsConstructor;

import org.neatore.onamnotifier.annotation.AdminAccess;
import org.neatore.onamnotifier.annotation.PublicAccess;
import org.neatore.onamnotifier.dto.AuthDto;
import org.neatore.onamnotifier.service.TokenService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class TokenController {
    private final Duration EXPIRATION_DURATION = Duration.ofDays(30);

    private final TokenService tokenService;

    @PostMapping
    @PublicAccess
    public ResponseEntity<Void> login(@RequestBody AuthDto.LoginRequest authDto) {
        if (
                authDto.auth_code() == null || authDto.redirect_uri() == null || authDto.auth_code().isEmpty() || authDto.redirect_uri().isEmpty()
        ) return ResponseEntity.badRequest().build();

        TokenService.JwtToken jwtToken = this.tokenService.createTokenAuth(authDto, EXPIRATION_DURATION.toMillis());
        ResponseCookie jwtCookie = ResponseCookie.from("ONN_ACCESS", jwtToken.jwtToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("none")
                .maxAge(EXPIRATION_DURATION)
                .build();

        ResponseCookie csrfCookie = ResponseCookie.from("ONN_CSRF", jwtToken.csrfToken())
                .secure(true)
                .path("/")
                .sameSite("none")
                .maxAge(EXPIRATION_DURATION)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, jwtCookie.toString());
        headers.add(HttpHeaders.SET_COOKIE, csrfCookie.toString());

        return ResponseEntity.ok().headers(headers).build();
    }

    @DeleteMapping
    @AdminAccess
    public ResponseEntity<Void> logout() {
        // 말소 쿠키 작성
        ResponseCookie jwtCookie = ResponseCookie.from("ONN_ACCESS")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("none")
                .maxAge(0)
                .build();

        ResponseCookie csrfCookie = ResponseCookie.from("ONN_CSRF")
                .secure(true)
                .path("/")
                .sameSite("none")
                .maxAge(0)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, jwtCookie.toString());
        headers.add(HttpHeaders.SET_COOKIE, csrfCookie.toString());

        return ResponseEntity.ok().headers(headers).build();
    }
}
