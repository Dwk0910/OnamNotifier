package org.neatore.onamnotifier.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;

import org.json.JSONObject;

import org.neatore.onamnotifier.dto.AuthDto;
import org.neatore.onamnotifier.exception.AuthException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class TokenService {

    @Value("${GOOGLE_OAUTH_CLIENT_ID}")
    private String clientId;

    @Value("${GOOGLE_OAUTH_CLIENT_SECRET}")
    private String clientSecret;

    @Value("${JWT_TOKEN_SECRET}")
    private String SECRET_KEY;

    @Value("#{'${ADMIN_EMAILS}'.split(',')}")
    private List<String> ADMIN_EMAILS;

    private SecretKey secretKey;

    @PostConstruct
    protected void init() {
        this.secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public record JwtToken(String jwtToken, String csrfToken) {}

    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(this.secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return ADMIN_EMAILS.contains(claims.getSubject());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getPayload(String token) {
        return Jwts.parser()
                .verifyWith(this.secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public JwtToken createToken(String email, long exp) {
        if (!ADMIN_EMAILS.contains(email)) throw new IllegalArgumentException("Invalid email: THe provided email " + email + " is not authorized to create a token.");

        UUID csrfToken = UUID.randomUUID();

        return new JwtToken(
                Jwts.builder()
                        .subject(email)
                        .claim("csrf_token", csrfToken.toString())
                        .issuedAt(new Date())
                        .expiration(new Date(System.currentTimeMillis() + exp))
                        .signWith(this.secretKey)
                        .compact(),
                csrfToken.toString()
        );
    }

    public String getCsrfToken(String token) {
        return this.getPayload(token).get("csrf_token").toString();
    }

    public @Nullable String getUserEmail(String token) {
        return this.getPayload(token).getSubject();
    }

    public JwtToken getTokenAuth(AuthDto.LoginRequest authDto, long exp) {
        try {
            RestClient rc = RestClient.create();

            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("code", authDto.auth_code());
            formData.add("client_id", clientId);
            formData.add("client_secret", clientSecret);
            formData.add("redirect_uri", authDto.redirect_uri());
            formData.add("grant_type", "authorization_code");

            String accessToken_ = rc.post()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("https")
                            .host("oauth2.googleapis.com")
                            .path("/token")
                            .build())
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(formData)
                    .retrieve()
                    .body(String.class);
            String accessToken = new JSONObject(Objects.requireNonNull(accessToken_)).getString("access_token");

            String userEmail_ = rc.get()
                    .uri(uribuilder -> uribuilder
                            .scheme("https")
                            .host("www.googleapis.com")
                            .path("/oauth2/v3/userinfo")
                            .build())
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .body(String.class);
            String userEmail = new JSONObject(Objects.requireNonNull(userEmail_)).getString("email");

            return this.createToken(userEmail, exp);
        } catch (Exception e) {
            if (e instanceof HttpClientErrorException.Unauthorized ex) {
                JSONObject obj = new JSONObject(ex.getResponseBodyAsString());
                if (!obj.optString("error").equals("invalid_grant")) throw new IllegalStateException("Unexpected error has occured while authenticating user : " + e);
            }

            throw new AuthException("Failed to authenticate user : " + e);
        }
    }
}
