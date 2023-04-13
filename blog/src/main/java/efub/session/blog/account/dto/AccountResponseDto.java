package efub.session.blog.account.dto;

import efub.session.blog.account.domain.Account;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountResponseDto {
    private Long accountId;
    private String email;
    private String nickname;
    private String bio;

    public AccountResponseDto(Long accountId, String email, String nickname, String bio) {
        this.accountId = accountId;
        this.email = email;
        this.nickname = nickname;
        this.bio = bio;
    }
    public static AccountResponseDto from(Account account) {
        return new AccountResponseDto(account.getAccountId(),
                account.getEmail(),
                account.getNickname(),
                account.getBio());
    }
}