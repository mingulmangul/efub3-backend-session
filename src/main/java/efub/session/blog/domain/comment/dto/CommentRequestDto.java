package efub.session.blog.domain.comment.dto;

import efub.session.blog.domain.account.domain.Account;
import efub.session.blog.domain.comment.domain.Comment;
import efub.session.blog.domain.post.domain.Post;

import javax.validation.constraints.NotNull;

// 애노테이션 추가하기!
public class CommentRequestDto {

    @NotNull(message = "작성자 ID를 입력해주세요.")
    private Long accountId;

    @NotNull(message = "내용을 입력해주세요.")
    private String content;

    public Comment toEntity(Post post, Account writer) {
        return Comment.builder()
                .post(post)
                .writer(writer)
                .content(this.content)
                .build();
    }
}
