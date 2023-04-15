package com.efub.blogsession.domain.post.controller;

import com.efub.blogsession.domain.post.domain.Post;
import com.efub.blogsession.domain.post.dto.PostModifyRequestDto;
import com.efub.blogsession.domain.post.dto.PostRequestDto;
import com.efub.blogsession.domain.post.dto.PostResponseDto;
import com.efub.blogsession.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public PostResponseDto postAdd(@RequestBody PostRequestDto requestDto) {   // request가 JSON으로 들어간다는 의미
        Post post = postService.addPost(requestDto);
        return new PostResponseDto(post);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<PostResponseDto> postListFind() {
        List<Post> postList = postService.findPostList();
        List<PostResponseDto> responseDtoList = new ArrayList<>();

        for (Post post : postList) {
            responseDtoList.add(new PostResponseDto(post));
        }
        return responseDtoList;
    }

    @GetMapping("/{postId}")
    @ResponseStatus(value = HttpStatus.OK)
    public PostResponseDto postFind(@PathVariable Long postId) {
        Post post = postService.findPost(postId);
        return new PostResponseDto(post);
    }

    @DeleteMapping("/{postId}/{accountId}")
    @ResponseStatus(value = HttpStatus.OK)
    public String postRemove(@PathVariable Long postId, @RequestParam Long accountId) {
        postService.removePost(postId, accountId);
        return "성공적으로 삭제되었습니다.";
    }

    @PutMapping("/{postId}")
    @ResponseStatus(value = HttpStatus.OK)
    public PostResponseDto postModify(@PathVariable Long postId, @RequestBody PostModifyRequestDto requestDto) {
        Post post = postService.modifyPost(postId, requestDto);
        return new PostResponseDto(post);
    }
}