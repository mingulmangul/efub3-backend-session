package efub.session.blog.domain.account.controller;

import efub.session.blog.domain.account.domain.Account;
import efub.session.blog.domain.account.dto.response.AccountCommentsResponseDto;
import efub.session.blog.domain.account.service.AccountService;
import efub.session.blog.domain.comment.domain.Comment;
import efub.session.blog.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts/{accountId}/comments")
public class AccountCommentController {

    private final CommentService commentService;    // 의존관계 : AccountCommentController -> CommentService
    private final AccountService accountService;    // 의존관계 : AccountCommentController -> AccountService

    // 특정 유저의 댓글 목록 조회
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public AccountCommentsResponseDto readAccountComments(@PathVariable Long accountId) {
        Account writer = accountService.findAccountById(accountId);
        List<Comment> commentList = commentService.findCommentListByWriter(writer);
        return AccountCommentsResponseDto.of(writer.getNickname(), commentList);
    }
}
