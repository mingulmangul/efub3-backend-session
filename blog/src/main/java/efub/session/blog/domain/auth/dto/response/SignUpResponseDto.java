package efub.session.blog.domain.auth.dto.response;

import efub.session.blog.domain.account.domain.Account;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpResponseDto {

	private Long accountId;
	private String email;
	private String nickname;
	private String bio;

	public SignUpResponseDto(Account account) {
		this.accountId = account.getAccountId();
		this.email = account.getEmail();
		this.nickname = account.getNickname();
		this.bio = account.getBio();
	}
}
