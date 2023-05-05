package efub.session.blog.domain.post.dto.response;

import efub.session.blog.domain.post.domain.Post;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PostListResponseDto {

    private List<PostResponseDto> posts;
    private Integer count;

    public static PostListResponseDto of(List<Post> postList) {
        return PostListResponseDto.builder()
                .posts(postList.stream().map(PostResponseDto::from).collect(Collectors.toList()))
                .count(postList.size())
                .build();
    }
}
