package org.neatore.onamnotifier.dto;

public abstract class PostDto {
    public record PostRequest(String title, String content) {}
    public record QueryResponse(Long id, String title, String content) {}
}
