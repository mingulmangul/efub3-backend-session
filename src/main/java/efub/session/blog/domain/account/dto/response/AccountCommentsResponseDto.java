package efub.session.blog.domain.account.dto.response;

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
public class AccountCommentsResponseDto {

    private String writerName;
    private List<AccountComment> comments;
    private Integer count;

    public static AccountCommentsResponseDto of(String writerName, List<Comment> commentList) {
        return AccountCommentsResponseDto.builder()
                .writerName(writerName)
                .comments(commentList.stream().map(AccountComment::of).collect(Collectors.toList()))
                .count(commentList.size())
                .build();
    }

    @Getter
    public static class AccountComment {

        private Long commentId;
        private Long postId;
        private String postTitle;
        private String content;
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;

        public AccountComment(Comment comment) {
            this.commentId = comment.getCommentId();
            this.postId = comment.getPost().getPostId();
            this.postTitle = comment.getPost().getTitle();
            this.content = comment.getContent();
            this.createdDate = comment.getCreatedDate();
            this.modifiedDate = comment.getModifiedDate();
        }

        public static AccountComment of(Comment comment) {
            return new AccountComment(comment);
        }
    }
}
