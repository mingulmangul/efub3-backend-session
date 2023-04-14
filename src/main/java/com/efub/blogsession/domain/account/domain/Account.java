package com.efub.blogsession.domain.account.domain;

import com.efub.blogsession.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

import static com.efub.blogsession.domain.account.domain.AccountStatus.REGISTERED;
import static com.efub.blogsession.domain.account.domain.AccountStatus.UNREGISTERED;


@Entity//해당 클래스에 있는 내부변수에 모두 @Column을 내부적으로 포함 -> 옵셥없으면 생략 가능
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자의 접근 제어를 PROTECTED로 설정해놓게 되면 무분별한 객체 생성에 대해 한번 더 체크할 수 있는 수단
@Getter
public class Account extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id", updatable = false)
	private Long accountId;

	@Column(nullable = false, length = 60)//DB에 저장될 때 조건(물리적인 데이터베이스 컬럼의 특성을 나타냄), 유효성 체크를 해주지는 않음
	private String email;


	@Column(nullable = false)
	private String encodedPassword;

	@Column(nullable = false, updatable = false, length = 16)
	private String nickname;// 닉네임 변경 불가

	private String bio;//length 따로 지정하지 않으면 기본적으로 255이다.

	@Enumerated(EnumType.STRING)
	private AccountStatus status;

	@Builder
	public Account(Long accountId, String email, String password, String nickname, String bio) {
		this.accountId = accountId;
		this.email = email;
		this.encodedPassword = password;
		this.nickname = nickname;
		this.bio = bio;
		this.status = REGISTERED;
	}

	public void updateAccount(String bio, String nickname){
		this.bio = bio;
		this.nickname = nickname;
	}

	public void withdrawAccount(){
		this.status = UNREGISTERED;
	}


}