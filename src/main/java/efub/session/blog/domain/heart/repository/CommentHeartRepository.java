package efub.session.blog.domain.heart.repository;

import efub.session.blog.domain.account.domain.Account;
import efub.session.blog.domain.comment.domain.Comment;
import efub.session.blog.domain.heart.domain.CommentHeart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentHeartRepository extends JpaRepository<CommentHeart, Long> {

    Integer countByComment(Comment comment);

    List<CommentHeart> findAllByWriter(Account writer);

    Boolean existsByWriterAndComment(Account writer, Comment comment);

    Optional<CommentHeart> findByWriterAndComment(Account writer, Comment comment);
}
