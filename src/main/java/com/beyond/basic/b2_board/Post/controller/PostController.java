package com.beyond.basic.b2_board.Post.controller;

import com.beyond.basic.b2_board.Author.DTO.CommonDto;
import com.beyond.basic.b2_board.Author.DTO.CommonErrorDto;
import com.beyond.basic.b2_board.Post.dto.PostCreateDto;
import com.beyond.basic.b2_board.Post.dto.PostDetailDto;
import com.beyond.basic.b2_board.Post.dto.PostListDto;
import com.beyond.basic.b2_board.Post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor

public class PostController {
    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody PostCreateDto postCreateDto) {
        postService.save(postCreateDto);
        return new ResponseEntity<>(new CommonDto("ok", HttpStatus.CREATED.value(), "post is created"), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<?> findAll() {
        List<PostListDto> postListDto = postService.findAll();
        return new ResponseEntity<>(new CommonDto(postListDto, HttpStatus.CREATED.value(), "문구"), HttpStatus.CREATED);
    }

    @GetMapping("/detail/{inputId}")
    public ResponseEntity<?> findById(@PathVariable Long inputId) {
        PostDetailDto postDetailDto = postService.findById(inputId);
        return new ResponseEntity<>(new CommonDto(postDetailDto, HttpStatus.CREATED.value(), "post is found"),HttpStatus.CREATED);

    }
}
