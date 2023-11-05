package efub.session.blog.domain.account.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import efub.session.blog.domain.account.domain.Account;
import efub.session.blog.domain.account.dto.AccountResponseDto;
import efub.session.blog.domain.account.dto.AccountUpdateRequestDto;
import efub.session.blog.domain.account.service.AccountService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
	private final AccountService accountService;

	/* 계정 조회 기능 (1명) */
	@GetMapping("/{accountId}")
	@ResponseStatus(value = HttpStatus.OK)
	public AccountResponseDto getAccount(@PathVariable Long accountId) {
		Account findAccount = accountService.findAccountById(accountId);
		return AccountResponseDto.from(findAccount);
	}

	/* 계정 프로필 수정 */
	@PatchMapping("/profile/{accountId}")
	@ResponseStatus(value = HttpStatus.OK)
	public AccountResponseDto update(@PathVariable final Long accountId,
		@RequestBody @Valid final AccountUpdateRequestDto requestDto) {
		Long id = accountService.update(accountId, requestDto);
		Account findAccount = accountService.findAccountById(id);
		return AccountResponseDto.from(findAccount);
	}

	/* 계정 삭제 (휴면 계정으로) */
	@PatchMapping("/{accountId}")
	@ResponseStatus(value = HttpStatus.OK)
	public String withdraw(@PathVariable long accountId) {
		accountService.withdraw(accountId);
		return "성공적으로 탈퇴되었습니다.";
	}

	/* 계정 삭제 (db에서도 삭제) */
	@DeleteMapping("/{accountId}")
	@ResponseStatus(value = HttpStatus.OK)
	public String delete(@PathVariable long accountId) {
		accountService.delete(accountId);
		return "성공적으로 탈퇴가 완료되었습니다";
	}
}
