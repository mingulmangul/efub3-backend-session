package efub.session.blog.domain.heart.repository;

import efub.session.blog.domain.account.domain.Account;
import efub.session.blog.domain.heart.domain.PostHeart;
import efub.session.blog.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostHeartRepository extends JpaRepository<PostHeart, Long> {
    // countBy: JPA에서 테이블 조회 시 Count 값을 가져오기 위해 사용하는 메소드
    Integer countByPost(Post post);

    // 작성자 기준으로 postheart 조회
    List<PostHeart> findAllByWriter(Account writer);

    // 작성자와 게시글이 있는지 확인
    Boolean existsByWriterAndPost(Account writer, Post post);

    // 작성자와 게시글 기준으로 postheart 조회
    Optional<PostHeart> findByWriterAndPost(Account writer, Post post);
}
