package com.vvornicova.posts.controller;


import com.vvornicova.posts.models.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/posts")
public class PostsController {

    private List<Post> postList = new ArrayList<>();
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping
    public List<Post> getAllPosts() {
        return postList;
    }

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody Post post) {
        String userServiceUrl = "http://users-container:9091/users/" + post.getUserId();

        ResponseEntity<Object> response = restTemplate.getForEntity(userServiceUrl, Object.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            postList.add(post);
            return ResponseEntity.status(HttpStatus.CREATED).body("Пост успешно создан.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь с указанным ID не найден.");
        }
    }

    @PutMapping
    public void updatePost(@RequestBody Post post) {
        postList.stream()
                .filter(p -> Objects.equals(p.getId(), post.getId()))
                .findFirst()
                .ifPresent(p -> {
                    p.setTitle(post.getTitle());
                    p.setContent(post.getContent());
                    p.setUserId(post.getUserId());
                });
    }

    @DeleteMapping
    public void deletePost(@RequestBody Long id) {
        postList.removeIf(p -> Objects.equals(p.getId(), id));
    }
}
