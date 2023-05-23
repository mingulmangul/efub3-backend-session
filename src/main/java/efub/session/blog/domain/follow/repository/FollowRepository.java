package efub.session.blog.domain.follow.repository;

import efub.session.blog.domain.account.domain.Account;
import efub.session.blog.domain.follow.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    // 팔로우 존재 확인
    Boolean existsByFollowerAndFollowing(Account follower, Account following);

    // 팔로우 조회
    Optional<Follow> findByFollower_AccountIdAndFollowing_AccountId(Long followerId, Long followingId);

    // 팔로우 목록 조회
    List<Follow> findAllByFollower(Account follower);

    List<Follow> findAllByFollowing(Account following);

}
