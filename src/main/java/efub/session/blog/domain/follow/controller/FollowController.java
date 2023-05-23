package efub.session.blog.domain.follow.controller;

import efub.session.blog.domain.account.domain.Account;
import efub.session.blog.domain.account.service.AccountService;
import efub.session.blog.domain.follow.domain.Follow;
import efub.session.blog.domain.follow.dto.FollowListResponseDto;
import efub.session.blog.domain.follow.dto.FollowRequestDto;
import efub.session.blog.domain.follow.dto.FollowStatusResponseDto;
import efub.session.blog.domain.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follows")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final AccountService accountService;

    // 팔로우 걸기
    @PostMapping("/{accountId}")
    @ResponseStatus(HttpStatus.CREATED)
    public FollowStatusResponseDto addFollow(@PathVariable final Long accountId, @RequestBody final FollowRequestDto requestDto) {
        followService.add(accountId, requestDto);
        boolean isFollow = followService.isFollowing(accountId, requestDto.getFollowingId());
        Account following = accountService.findAccountById(requestDto.getFollowingId());
        return FollowStatusResponseDto.builder()
                .following(following)
                .isFollow(isFollow)
                .build();
    }

    // 팔로우 목록 조회
    @GetMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public FollowListResponseDto getFollowList(@PathVariable final Long accountId) {
        List<Follow> followerList = followService.findFollowerList(accountId);
        List<Follow> followingList = followService.findFollowingList(accountId);
        return FollowListResponseDto.of(followerList, followingList);
    }

    // 팔로우 여부 조회
    @GetMapping("/{accountId}/search")
    @ResponseStatus(HttpStatus.OK)
    public FollowStatusResponseDto searchAccount(@PathVariable final Long accountId, @RequestParam final String email) {
        Account searchAccount = accountService.findAccountByEmail(email);
        boolean isFollow = followService.isFollowing(accountId, searchAccount.getAccountId());
        return FollowStatusResponseDto.builder()
                .following(searchAccount)
                .isFollow(isFollow)
                .build();
    }

    // 팔로우 삭제
    @DeleteMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public FollowStatusResponseDto deleteFollow(@PathVariable final Long accountId, @RequestParam final Long followingId) {
        followService.delete(accountId, followingId);
        Account findAccount = accountService.findAccountById(followingId);
        boolean isFollow = followService.isFollowing(accountId, followingId);
        return FollowStatusResponseDto.builder()
                .following(findAccount)
                .isFollow(isFollow)
                .build();
    }
}
