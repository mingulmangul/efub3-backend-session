package efub.session.blog.domain.comment.service;

import efub.session.blog.domain.account.domain.Account;
import efub.session.blog.domain.account.service.AccountService;
import efub.session.blog.domain.comment.domain.Comment;
import efub.session.blog.domain.comment.dto.CommentRequestDto;
import efub.session.blog.domain.comment.repository.CommentRepository;
import efub.session.blog.domain.post.domain.Post;
import efub.session.blog.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor    // 생성자를 통해 필요한 의존관계를 주입 받음
public class CommentService {

    private final CommentRepository commentRepository;  // 의존관계 : CommentService -> CommentRepository

    private final PostService postService;        // 의존관계 : CommentService -> PostService
    private final AccountService accountService;  // 의존관계 : CommentService -> AccountService

    // 댓글 작성
    public Long createComment(Long postId, CommentRequestDto requestDto) {
        Post post = postService.findPost(postId);
        Account writer = accountService.findAccountById(requestDto.getAccountId());
        return commentRepository.save(requestDto.toEntity(post, writer)).getCommentId();
    }

    // 댓글 조회 - ID별
    @Transactional(readOnly = true)
    public Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(("존재하지 않는 댓글입니다. ID=" + commentId)));
    }

    // 댓글 목록 조회 - 작성자별
    @Transactional(readOnly = true)
    public List<Comment> findCommentListByWriter(Account writer) {
        return commentRepository.findAllByWriter(writer);
    }

    // 댓글 목록 조회 - 게시글별
    @Transactional(readOnly = true)
    public List<Comment> findCommentListByPost(Long postId) {
        Post post = postService.findPost(postId);
        return commentRepository.findAllByPost(post);
    }

    // 댓글 수정
    public void updateComment(CommentRequestDto requestDto, Long commentId) {
        Comment comment = findCommentById(commentId);
        comment.updateComment(requestDto.getContent());
    }

    // 댓글 삭제
    public void deleteComment(Long commentId) {
        Comment comment = findCommentById(commentId);
        commentRepository.delete(comment);
    }
}
