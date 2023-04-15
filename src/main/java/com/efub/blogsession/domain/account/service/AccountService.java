package com.efub.blogsession.domain.account.service;

import com.efub.blogsession.domain.account.domain.Account;
import com.efub.blogsession.domain.account.dto.AccountUpdateRequestDto;
import com.efub.blogsession.domain.account.dto.SignUpRequestDto;
import com.efub.blogsession.domain.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service//서비스 레이어, 내부에서 자바 로직을 처리함
@RequiredArgsConstructor //final 키워드가 붙은 필드에 대해 생성자 자동 생성
public class AccountService {
	private final AccountRepository accountRepository;
	//private final BCryptPasswordEncoder passwordEncoder;

	public Long signUp(SignUpRequestDto requestDto){
		if (isExistedEmail(requestDto.getEmail())){
			throw new IllegalArgumentException("이미 존재하는 email입니다. " + requestDto.getEmail());
		}
		Account account = accountRepository.save(requestDto.toEntity());
		return account.getAccountId();
	}


	public Long update(Long accountId, AccountUpdateRequestDto requestDto){
		Account account = findById(accountId);
		account.updateAccount(requestDto.getBio(), requestDto.getNickname());
		return account.getAccountId();
	}


	public void delete(Long accountId) {
		Account account = findById(accountId);
		accountRepository.delete(account);
	}

	@Transactional
	public void withdraw(Long accountId) {
		Account account = findById(accountId);
		account.withdrawAccount();
	}

	@Transactional//TODO readOnly
	public Account findById(Long id) {
		return accountRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("해당 id 를 가진 Account 를 찾을 수 없습니다. id ="+id));
	}

	@Transactional//TODO:readOnly 적용
	public boolean isExistedEmail(String email){
		return accountRepository.existsByEmail(email);
	}





}
