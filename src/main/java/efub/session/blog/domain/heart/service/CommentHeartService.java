package efub.session.blog.domain.heart.service;

import efub.session.blog.domain.account.domain.Account;
import efub.session.blog.domain.account.dto.request.AccountInfoRequestDto;
import efub.session.blog.domain.account.service.AccountService;
import efub.session.blog.domain.comment.domain.Comment;
import efub.session.blog.domain.comment.service.CommentService;
import efub.session.blog.domain.heart.domain.CommentHeart;
import efub.session.blog.domain.heart.repository.CommentHeartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 4. 의존관계 주입
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentHeartService {
    private final CommentHeartRepository commentHeartRepository;
    private final CommentService commentService;
    private final AccountService accountService;

    public void create(Long commentId, AccountInfoRequestDto requestDto) {
        Account account = accountService.findAccountById(requestDto.getAccountId());
        Comment comment = commentService.findCommentById(commentId);

        // 한 유저가 한 게시물에 대해 한 번만 좋아요를 누를 수 있도록 제한
        if (isExistsByWriterAndComment(account, comment)) {
            throw new RuntimeException("이미 좋아요를 눌렀습니다.");
        }

        CommentHeart commentHeart = CommentHeart.builder()
                .writer(account)
                .comment(comment)
                .build();
        commentHeartRepository.save(commentHeart);
    }

    public void delete(Long commentId, Long accountId) {
        Comment comment = commentService.findCommentById(commentId);
        Account account = accountService.findAccountById(accountId);

        CommentHeart commentHeart = commentHeartRepository.findByWriterAndComment(account, comment)
                .orElseThrow(() -> new IllegalArgumentException("해당 좋아요가 없습니다."));

        commentHeartRepository.delete(commentHeart);
    }

    @Transactional(readOnly = true)
    public boolean isExistsByWriterAndComment(Account account, Comment comment) {
        return commentHeartRepository.existsByWriterAndComment(account, comment);
    }

    @Transactional(readOnly = true)
    public Integer countCommentHeart(Comment comment) {
        return commentHeartRepository.countByComment(comment);
    }
}