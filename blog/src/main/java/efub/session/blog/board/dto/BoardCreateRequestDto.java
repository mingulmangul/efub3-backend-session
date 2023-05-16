package efub.session.blog.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Getter
@NoArgsConstructor
public class BoardCreateRequestDto {

    @NotBlank(message = "게시판명은 필수로 입력해야 합니다.")
    private String title;

    private String description;

    private String pinned;

    private Long ownerId;

}
