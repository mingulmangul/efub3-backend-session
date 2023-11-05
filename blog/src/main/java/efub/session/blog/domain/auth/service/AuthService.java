package efub.session.blog.domain.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import efub.session.blog.domain.account.domain.Account;
import efub.session.blog.domain.account.repository.AccountRepository;
import efub.session.blog.domain.auth.dto.request.SignUpRequestDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final PasswordEncoder passwordEncoder;
	private final AccountRepository accountRepository;

	public Account signUp(SignUpRequestDto requestDto) {
		if (existsByEmail(requestDto.getEmail())) {
			throw new IllegalArgumentException("중복된 이메일이 있습니다. " + requestDto.getEmail());
		}
		String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
		return accountRepository.save(requestDto.toEntity(encodedPassword));
	}

	public boolean existsByEmail(String email) {
		return accountRepository.existsByEmail(email);
	}
}
