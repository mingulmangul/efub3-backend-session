package efub.session.blog.domain.heart.service;

import efub.session.blog.domain.account.domain.Account;
import efub.session.blog.domain.account.service.AccountService;
import efub.session.blog.domain.heart.domain.PostHeart;
import efub.session.blog.domain.heart.repository.PostHeartRepository;
import efub.session.blog.domain.post.domain.Post;
import efub.session.blog.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j // Simple Logging Facade for Java, 다양한 로깅 프레임 워크에 대한 추상화(인터페이스) 역할을 하는 라이브러리
@Service
@Transactional
@RequiredArgsConstructor
public class PostHeartService {

    private final PostHeartRepository postHeartRepository;
    private final PostService postService;
    private final AccountService accountService;

    public void create(Long postId, Long accountId) {
        // accountId 참조
        Account account = accountService.findAccountById(accountId);

        // postId 참조
        Post post = postService.findPost(postId);

        // 작성자와 게시글이 존재하는지 확인
        // 한 유저가 한 게시물에 대해 한 번만 좋아요를 누를 수 있도록 제한
        if (isExistsByWriterAndPost(account, post)) {
            throw new RuntimeException("이미 좋아요를 누른 게시물입니다.");
        }

        // Builder 패턴 적용
        PostHeart postHeart = PostHeart.builder()
                .post(post)
                .writer(account)
                .build();

        // postHeart 객체를 DB에 저장
        postHeartRepository.save(postHeart);
    }

    public void delete(Long postId, Long accountId) {
        // postId 참조
        Post post = postService.findPost(postId);

        // accountId 참조
        Account account = accountService.findAccountById(accountId);

        // 작성자와 게시글이 존재하는지 확인
        PostHeart postHeart = postHeartRepository.findByWriterAndPost(account, post)
                .orElseThrow(() -> new RuntimeException("좋아요가 존재하지 않습니다."));
        // postHeart 객체를 DB에서 삭제
        postHeartRepository.delete(postHeart);
    }

    public Boolean isHeart(Long accountId, Post post) {
        // accountId 참조
        Account account = accountService.findAccountById(accountId);
        return isExistsByWriterAndPost(account, post);
    }

    @Transactional(readOnly = true)
    public Boolean isExistsByWriterAndPost(Account account, Post post) {
        return postHeartRepository.existsByWriterAndPost(account, post);
    }

    @Transactional(readOnly = true)
    public Integer countPostHeart(Post post) {
        return postHeartRepository.countByPost(post);
    }

}