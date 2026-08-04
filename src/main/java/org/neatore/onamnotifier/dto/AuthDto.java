package org.neatore.onamnotifier.dto;

public class AuthDto {
    public record LoginRequest(
            String auth_code,
            String redirect_uri,
            String state
    ) {}
}
