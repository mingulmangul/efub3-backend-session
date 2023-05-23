package efub.session.blog.domain.follow.dto;

import efub.session.blog.domain.account.domain.Account;
import efub.session.blog.domain.follow.domain.Follow;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowRequestDto {

    private Long followingId;

    public Follow toEntity(Account follower, Account following) {
        return Follow.builder()
                .follower(follower)
                .following(following)
                .build();
    }
}
