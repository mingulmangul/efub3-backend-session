package efub.session.blog.domain.comment.controller;

import efub.session.blog.domain.account.dto.request.AccountInfoRequestDto;
import efub.session.blog.domain.comment.domain.Comment;
import efub.session.blog.domain.comment.dto.CommentRequestDto;
import efub.session.blog.domain.comment.dto.CommentResponseDto;
import efub.session.blog.domain.comment.service.CommentService;
import efub.session.blog.domain.heart.service.CommentHeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor    // 생성자를 통한 의존관계 주입
@RequestMapping("/comments/{commentId}")
public class CommentController {

    private final CommentService commentService;    // 의존관계: CommentController -> CommentService
    private final CommentHeartService commentHeartService;

    // 댓글 수정
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseDto updateComment(@PathVariable final Long commentId,
                                            @RequestBody final CommentRequestDto requestDto) {
        commentService.updateComment(requestDto, commentId);
        Comment comment = commentService.findCommentById(commentId);
        return CommentResponseDto.of(comment);
    }

    // 댓글 삭제
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public String deleteComment(@PathVariable final Long commentId) {
        commentService.deleteComment(commentId);
        return "댓글이 성공적으로 삭제되었습니다.";
    }

    // 댓글 좋아요 생성
    @PostMapping("/hearts")
    @ResponseStatus(HttpStatus.CREATED)
    public String createCommentHeart(@PathVariable final Long commentId, @RequestBody final AccountInfoRequestDto requestDto) {
        commentHeartService.create(commentId, requestDto);
        return "좋아요를 눌렀습니다.";
    }

    // 댓글 좋아요 삭제
    @DeleteMapping("/hearts")
    @ResponseStatus(HttpStatus.OK)
    public String deleteCommentHeart(@PathVariable final Long commentId, @RequestParam final Long accountId) {
        commentHeartService.delete(commentId, accountId);
        return "좋아요가 취소되었습니다.";
    }
}
