package com.efub.blogsession.domain.post.dto;

import com.efub.blogsession.domain.post.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private Long postId;
    private String writerName;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public PostResponseDto(Post post) {
        this.postId = post.getPostId();
        this.writerName = post.getWriter().getNickname();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdDate = post.getCreatedDate();
        this.modifiedDate = post.getModifiedDate();
    }
}