package efub.session.blog.domain.follow.dto;

import efub.session.blog.domain.follow.domain.Follow;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FollowListResponseDto {

    private Integer followerCount;
    private List<FollowInfo> followerList;
    private Integer followingCount;
    private List<FollowInfo> followingList;

    public static FollowListResponseDto of(List<Follow> followerList, List<Follow> followingList) {
        return FollowListResponseDto.builder()
                .followerCount(followerList.size())
                .followerList(followerList.stream().map(FollowInfo::ofFollower).collect(Collectors.toList()))
                .followingCount(followingList.size())
                .followingList(followingList.stream().map(FollowInfo::ofFollowing).collect(Collectors.toList()))
                .build();
    }

    // 팔로우 정보
    @Getter
    public static class FollowInfo {
        private final Long followId;
        private final String nickname;
        private final String email;

        public FollowInfo(Long followId, String nickname, String email) {
            this.followId = followId;
            this.nickname = nickname;
            this.email = email;
        }

        public static FollowInfo ofFollower(Follow follow) {
            return new FollowInfo(follow.getFollowId(),
                    follow.getFollower().getNickname(),
                    follow.getFollower().getEmail());
        }

        public static FollowInfo ofFollowing(Follow follow) {
            return new FollowInfo(follow.getFollowId(),
                    follow.getFollowing().getNickname(),
                    follow.getFollowing().getEmail());
        }
    }

    // 팔로워 한 명
    public static class SingleFollower {
        private final Long followId;
        private final String followerNickname;
        private final String email;

        public SingleFollower(Follow follow) {
            this.followId = follow.getFollowId();
            this.followerNickname = follow.getFollower().getNickname();
            this.email = follow.getFollower().getEmail();
        }
    }

    // 팔로잉 한 명
    public static class SingleFollowing {
        private final Long followId;
        private final String followingNickname;
        private final String email;

        public SingleFollowing(Follow follow) {
            this.followId = follow.getFollowId();
            this.followingNickname = follow.getFollowing().getNickname();
            this.email = follow.getFollowing().getEmail();
        }
    }

}
