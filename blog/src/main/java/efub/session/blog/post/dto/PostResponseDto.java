package efub.session.blog.post.dto;

import efub.session.blog.post.domain.Post;
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
    private LocalDateTime createdDime;
    private LocalDateTime modifiedDate;

    public PostResponseDto(Post post) {
        this.postId = post.getPostId();
        this.writerName = post.getWriter().getNickname();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdDime = post.getCreatedDate();
        this.modifiedDate = post.getModifiedDate();
    }
}
