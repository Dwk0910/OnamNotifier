package org.neatore.onamnotifier.dto;

import org.neatore.onamnotifier.db.Post;

import java.time.LocalDateTime;

public abstract class PostDto {
    public record PostRequest(String title, String content, Post.PostCategory category) {}
    public record QueryResponse(Long id, String title, String content, LocalDateTime createdAt) {}
}
