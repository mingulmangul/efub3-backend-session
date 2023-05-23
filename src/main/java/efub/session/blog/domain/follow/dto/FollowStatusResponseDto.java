package efub.session.blog.domain.follow.dto;

import efub.session.blog.domain.account.domain.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowStatusResponseDto {

    private Long accountId;
    private String nickname;
    private String email;
    private String status;

    @Builder
    public FollowStatusResponseDto(Account following, boolean isFollow) {
        this.accountId = following.getAccountId();
        this.nickname = following.getNickname();
        this.email = following.getEmail();

        if (isFollow) status = "FOLLOW";
        else status = "UNFOLLOW";
    }
}
