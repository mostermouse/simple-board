package com.jpa.simpleboard.post.controller;

import com.jpa.simpleboard.post.db.PostEntity;
import com.jpa.simpleboard.post.model.PostRequest;
import com.jpa.simpleboard.post.model.PostViewRequest;
import com.jpa.simpleboard.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostApiController {
    private final PostService postService;
    @PostMapping("")
    public PostEntity create(
            @Valid
            @RequestBody PostRequest postRequest
    ){
        return postService.create(postRequest);


    }
    @PostMapping("/view")
    public PostEntity view(
            @Valid
        @RequestBody PostViewRequest postViewRequest

            ){
        return postService.view(postViewRequest);
    }
    @GetMapping("/all")
    public List<PostEntity> list(

    ){
        return postService.all();
    }
    @PostMapping("/delete")
    public void delete(
        @Valid
        @RequestBody PostViewRequest postViewRequest
    ){
        postService.delete(postViewRequest);
    }
}
