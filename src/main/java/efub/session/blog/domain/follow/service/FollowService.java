package efub.session.blog.domain.follow.service;

import efub.session.blog.domain.account.domain.Account;
import efub.session.blog.domain.account.service.AccountService;
import efub.session.blog.domain.follow.domain.Follow;
import efub.session.blog.domain.follow.dto.FollowRequestDto;
import efub.session.blog.domain.follow.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final AccountService accountService;

    // 팔로우 추가
    public void add(Long accountId, FollowRequestDto requestDto) {
        Account follower = accountService.findAccountById(accountId);
        Account following = accountService.findAccountById(requestDto.getFollowingId());
        Follow follow = followRepository.save(requestDto.toEntity(follower, following));
    }

    // 팔로우 여부
    @Transactional(readOnly = true)
    public boolean isFollowing(Long accountId, Long followingId) {
        Account follower = accountService.findAccountById(accountId);
        Account following = accountService.findAccountById(followingId);
        return followRepository.existsByFollowerAndFollowing(follower, following);
    }

    // 팔로워 목록
    @Transactional(readOnly = true)
    public List<Follow> findFollowerList(Long accountId) {
        Account account = accountService.findAccountById(accountId);
        return followRepository.findAllByFollowing(account);
    }

    // 팔로잉 목록
    @Transactional(readOnly = true)
    public List<Follow> findFollowingList(Long accountId) {
        Account account = accountService.findAccountById(accountId);
        return followRepository.findAllByFollower(account);
    }

    // 팔로우 취소
    public void delete(Long accountId, Long followingId) {
        Follow follow = followRepository.findByFollower_AccountIdAndFollowing_AccountId(accountId, followingId)
                .orElseThrow(() -> new EntityNotFoundException("팔로우 정보가 존재하지 않습니다."));
        followRepository.delete(follow);
    }

}
