package efub.session.blog.domain.account.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import efub.session.blog.domain.account.domain.Account;
import efub.session.blog.domain.account.dto.AccountUpdateRequestDto;
import efub.session.blog.domain.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {
	private final AccountRepository accountRepository;

	@Transactional(readOnly = true)
	public Account findAccountById(Long id) {
		return accountRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 Account를 찾을 수 없습니다.id=" + id));
	}

	public Long update(Long accountId, AccountUpdateRequestDto requestDto) {
		Account account = findAccountById(accountId);
		account.updateAccount(requestDto.getBio(), requestDto.getNickname());
		return account.getAccountId();
	}

	@Transactional
	public void withdraw(Long accountId) {
		Account account = findAccountById(accountId);
		account.withdrawAccount();
	}

	public void delete(Long accountId) {
		Account account = findAccountById(accountId);
		accountRepository.delete(account);
	}
}
