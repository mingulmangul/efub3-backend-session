package efub.session.blog.domain.account.controller;

import efub.session.blog.domain.post.domain.Post;
import efub.session.blog.domain.post.dto.response.PostListResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

// url: /accounts/{accountId}/posts
public class AccountPostController {

    // 의존관계 : AccountCommentController -> PostService
    // 의존관계 : AccountCommentController -> AccountService

    // 특정 유저의 게시글 목록 조회
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PostListResponseDto readAccountPosts(@PathVariable Long accountId) {
        List<Post> postList = postService.findPostListByWriter(accountId);
        return PostListResponseDto.of(postList);
    }
}
