package com.efub.blogsession.domain.account.dto;


import com.efub.blogsession.domain.account.domain.Account;
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


	public AccountResponseDto(Account account) {
		this.accountId = account.getAccountId();
		this.email = account.getEmail();
		this.nickname = account.getNickname();
		this.bio = account.getBio();
	}
}
