package efub.session.blog.account.dto;

import lombok.Getter;

@Getter
public class PostModifyRequestDto {
    private Long accountId;
    private String title;
    private String content;


}
