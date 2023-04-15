package com.efub.blogsession.domain.post.service;

import com.efub.blogsession.domain.account.domain.Account;
import com.efub.blogsession.domain.account.repository.AccountRepository;
import com.efub.blogsession.domain.post.domain.Post;
import com.efub.blogsession.domain.post.dto.PostModifyRequestDto;
import com.efub.blogsession.domain.post.dto.PostRequestDto;
import com.efub.blogsession.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {

    private final AccountRepository accountRepository;

    private final PostRepository postRepository;

    public Post addPost(PostRequestDto requestDto){
        Account writer = accountRepository.findById(requestDto.getAccountId())
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 계정입니다."));
        return postRepository.save(
                Post.builder()
                        .title(requestDto.getTitle())
                        .content(requestDto.getContent())
                        .writer(writer)
                        .build()
        );

    }

    public List<Post> findPostList() {
        return postRepository.findAll();
    }

    public Post findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 게시글 입니다."));
    }

    public void removePost(Long postId, Long accountId) {
        Post post = postRepository.findByPostIdAndAndWriter_AccountId(postId,accountId)
                .orElseThrow(()->new IllegalArgumentException("잘못된 접근입니다."));
        postRepository.delete(post);
    }

    public Post modifyPost(Long postId, PostModifyRequestDto requestDto) {
        Post post = postRepository.findByPostIdAndAndWriter_AccountId(postId,requestDto.getAccountId())
                .orElseThrow(()->new IllegalArgumentException("잘못된 접근입니다."));
        post.updatePost(requestDto);
        return post;
    }
}
