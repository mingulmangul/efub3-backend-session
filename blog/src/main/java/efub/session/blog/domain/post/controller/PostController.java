package efub.session.blog.domain.post.controller;

import efub.session.blog.domain.post.domain.Post;
import efub.session.blog.domain.post.service.PostService;
import efub.session.blog.domain.post.dto.PostModifyRequestDto;
import efub.session.blog.domain.post.dto.PostRequestDto;
import efub.session.blog.domain.post.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public PostResponseDto postAdd(@RequestBody PostRequestDto requestDto) {   // request가 JSON으로 들어간다는 의미
        Post post = postService.addPost(requestDto);
        return new PostResponseDto(post);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<PostResponseDto> postListFind() {
        List<Post> postList = postService.findPostList();
        List<PostResponseDto> responseDtoList = new ArrayList<>();

        for (Post post : postList) {    // 스트림으로 한 줄 코드로 바꿀 수 있음
            responseDtoList.add(new PostResponseDto(post));
        }
        return responseDtoList;
    }

    @GetMapping("/{postId}")
    @ResponseStatus(value = HttpStatus.OK)
    public PostResponseDto postFind(@PathVariable Long postId) {
        Post post = postService.findPost(postId);
        return new PostResponseDto(post);
    }

    @DeleteMapping("/{postId}/{accountId}")
    @ResponseStatus(value = HttpStatus.OK)
    public String postRemove(@PathVariable Long postId, @RequestParam Long accountId) {
        postService.removePost(postId, accountId);
        return "성공적으로 삭제되었습니다.";
    }

    @PutMapping("/{postId}")
    @ResponseStatus(value = HttpStatus.OK)
    public PostResponseDto postModify(@PathVariable Long postId, @RequestBody PostModifyRequestDto requestDto) {
        Post post = postService.modifyPost(postId, requestDto);
        return new PostResponseDto(post);
    }
}
