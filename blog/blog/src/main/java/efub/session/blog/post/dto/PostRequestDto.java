package efub.session.blog.post.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private Long accountId;
    private String title;
    private String content;
}
