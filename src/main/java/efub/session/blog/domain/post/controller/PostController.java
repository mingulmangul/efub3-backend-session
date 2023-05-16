package efub.session.blog.domain.post.controller;

import efub.session.blog.domain.heart.dto.HeartRequestDto;
import efub.session.blog.domain.heart.service.PostHeartService;
import efub.session.blog.domain.post.domain.Post;
import efub.session.blog.domain.post.dto.request.PostModifyRequestDto;
import efub.session.blog.domain.post.dto.request.PostRequestDto;
import efub.session.blog.domain.post.dto.response.PostResponseDto;
import efub.session.blog.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostHeartService postHeartService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public PostResponseDto postAdd(@RequestBody PostRequestDto postRequestDto) {
        Post post = postService.addPost(postRequestDto);
        return PostResponseDto.from(post);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<PostResponseDto> postListFind() {
        List<Post> postList = postService.findPostList();
        return postList.stream().map(PostResponseDto::from).collect(Collectors.toList());
    }

    @GetMapping("/{postId}")
    @ResponseStatus(value = HttpStatus.OK)
    public PostResponseDto postFind(@PathVariable Long postId) {
        Post post = postService.findPost(postId);
        return PostResponseDto.from(post);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(value = HttpStatus.OK)
    public String postRemove(@PathVariable Long postId, @RequestParam Long accountId) {
        postService.removePost(postId, accountId);
        return "성공적으로 삭제가 완료되었습니다.";
    }

    @PutMapping("/{postId}")
    @ResponseStatus(value = HttpStatus.OK)
    public PostResponseDto postModify(@PathVariable Long postId, @RequestBody PostModifyRequestDto requestDto) {
        Post post = postService.modifyPost(postId, requestDto);
        return PostResponseDto.from(post);
    }

    // 게시글 좋아요 생성
    @PostMapping("/{postId}/hearts")
    @ResponseStatus(HttpStatus.CREATED)
    public String createPostHeart(@PathVariable final Long postId, @RequestBody final HeartRequestDto requestDto) {
        postHeartService.create(postId, requestDto.getAccountId());
        return "좋아요를 눌렀습니다.";
    }

    // 게시글 좋아요 삭제
    @DeleteMapping("/{postId}/hearts")
    @ResponseStatus(HttpStatus.OK)
    public String deletePostHeart(@PathVariable final Long postId, @RequestParam final Long accountId) {
        postHeartService.delete(postId, accountId);
        return "좋아요가 취소되었습니다.";
    }
}
