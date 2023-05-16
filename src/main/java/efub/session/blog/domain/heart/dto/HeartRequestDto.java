package efub.session.blog.domain.heart.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HeartRequestDto {

    // 작성자, not null
    @NotNull(message = "작성자는 필수로 입력되어야 합니다.")
    private Long accountId;

    // builder 패턴 적용
    @Builder
    public HeartRequestDto(Long accountId) {
        this.accountId = accountId;
    }
}
