package org.neatore.onamnotifier.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.neatore.onamnotifier.db.Post;
import org.neatore.onamnotifier.db.PostRepository;

import org.neatore.onamnotifier.dto.PostDto;
import org.neatore.onamnotifier.exception.QueryNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository repository;

    @Transactional
    public Long create(PostDto.PostRequest request) {
        Post post = new Post(request.title(), request.content(), request.category());
        this.repository.save(post);
        return post.getId();
    }

    @Transactional
    public void remove(Long id) {
        this.repository.deleteById(id);
    }

    public PostDto.QueryResponse query(Long id) {
        Post post = this.repository.findById(id).orElseThrow(() -> new QueryNotFoundException(id.toString()));
        return new PostDto.QueryResponse(post.getId(), post.getTitle(), post.getContent(), post.getCategory(), post.getCreatedAt());
    }

    public List<PostDto.QueryResponse> queryAll() {
        List<Post> posts = this.repository.findAll();
        return posts.stream()
                .map(post -> new PostDto.QueryResponse(post.getId(), post.getTitle(), post.getContent(), post.getCategory(), post.getCreatedAt()))
                .toList();
    }
}
