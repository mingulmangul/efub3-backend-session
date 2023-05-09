package efub.session.blog.domain.comment.repository;

import efub.session.blog.domain.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 작성자(writer)별 댓글 목록 조회

    // 게시글(post)별 댓글 목록 조회
}
