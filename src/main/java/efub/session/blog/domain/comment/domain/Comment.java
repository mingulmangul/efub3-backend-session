package efub.session.blog.domain.comment.domain;

import efub.session.blog.domain.account.domain.Account;
import efub.session.blog.domain.post.domain.Post;
import efub.session.blog.global.entity.BaseTimeEntity;

public class Comment extends BaseTimeEntity {

    private Long commentId;

    private String content;

    private Post post;

    private Account writer;

    // @Builder
}
