package efub.session.blog.domain.post.dto.response;

import efub.session.blog.domain.comment.domain.Comment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PostCommentsResponseDto {

    private Long postId;
    private List<PostComment> comments;
    private Integer count;

    public static PostCommentsResponseDto of(Long postId, List<Comment> commentList) {
        return PostCommentsResponseDto.builder()
                .postId(postId)
                .comments(commentList.stream().map(PostComment::of).collect(Collectors.toList()))
                .count(commentList.size())
                .build();
    }

    @Getter
    public static class PostComment {

        private Long commentId;
        private String writerName;
        private String content;
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;

        public PostComment(Comment comment) {
            this.commentId = comment.getCommentId();
            this.writerName = comment.getWriter().getNickname();
            this.content = comment.getContent();
            this.createdDate = comment.getCreatedDate();
            this.modifiedDate = comment.getModifiedDate();
        }

        public static PostComment of(Comment comment) {
            return new PostComment(comment);
        }
    }
}
