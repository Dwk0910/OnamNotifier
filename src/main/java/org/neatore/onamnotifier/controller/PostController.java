package org.neatore.onamnotifier.controller;

import lombok.RequiredArgsConstructor;

import org.neatore.onamnotifier.annotation.AdminAccess;
import org.neatore.onamnotifier.annotation.PublicAccess;
import org.neatore.onamnotifier.dto.PostDto;
import org.neatore.onamnotifier.service.PostService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    @AdminAccess
    public ResponseEntity<Void> createPost(@RequestBody PostDto.PostRequest request) {
        Long id = this.postService.create(request);
        return ResponseEntity.created(URI.create("posts/" + id)).build();
    }

    @DeleteMapping
    @AdminAccess
    public ResponseEntity<Void> removePost(@RequestParam Long postId) {
        this.postService.remove(postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PublicAccess
    public ResponseEntity<List<PostDto.QueryResponse>> getAllPosts() {
        return ResponseEntity.ok(this.postService.queryAll());
    }

    @GetMapping("/{id}")
    @PublicAccess
    public ResponseEntity<PostDto.QueryResponse> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(this.postService.query(id));
    }
}
