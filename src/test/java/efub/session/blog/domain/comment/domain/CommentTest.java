package efub.session.blog.domain.comment.domain;

import efub.session.blog.domain.account.domain.Account;
import efub.session.blog.domain.post.domain.Post;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class CommentTest {

    @Test
    void testToString() {
        Account account = new Account(1L, "example@email.com",
                "password123", "nickname", "안녕하세요!");
        Post post = new Post(1L, "제목", "내용", account);
        Comment comment = new Comment("댓글", post, account);
        List<Comment> commentList = new ArrayList<>();
        commentList.add(comment);
        post.setCommentList(commentList);
        post.toString();
    }
}
