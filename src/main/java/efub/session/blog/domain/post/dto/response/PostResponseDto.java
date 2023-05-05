package efub.session.blog.domain.post.dto.response;

import efub.session.blog.domain.post.domain.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponseDto {

    private Long postId;
    private String writerName;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public PostResponseDto(Long postId, String writerName, String title, String content, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.postId = postId;
        this.writerName = writerName;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static PostResponseDto from(Post post) {
        return new PostResponseDto(
                post.getPostId(),
                post.getWriter().getNickname(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedDate(),
                post.getModifiedDate());
    }
}
