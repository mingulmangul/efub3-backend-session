package efub.session.blog.domain.auth.dto.request;

import javax.validation.constraints.NotBlank;

import efub.session.blog.domain.account.domain.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequestDto {

	@NotBlank(message = "이메일은 필수입니다.")
	private String email;

	@NotBlank(message = "비밀번호는 필수입니다.")
	private String password;

	@NotBlank(message = "닉네임은 필수입니다. ")
	private String nickname;

	private String bio;

	@Builder
	public SignUpRequestDto(String email, String password, String nickname, String bio) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.bio = bio;
	}

	public Account toEntity(String encodedPassword) {
		return Account.builder()
			.email(this.email)
			.password(encodedPassword)
			.nickname(this.nickname)
			.bio(this.bio)
			.build();
	}
}
