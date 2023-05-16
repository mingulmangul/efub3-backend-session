package efub.session.blog.board.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardModifyRequestDto {
    @NotNull(message = "필수 입력값입니다.")    // NotBlank는 String 타입을 위한 어노테이션이므로, Long 타입에는 NotNull을 써야 한다!
    private Long ownerId;

    @Builder
    public BoardModifyRequestDto (Long ownerId) {
        this. ownerId = ownerId;
    }
}