package efub.session.blog.domain.auth.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import efub.session.blog.domain.account.domain.Account;
import efub.session.blog.domain.account.repository.AccountRepository;
import efub.session.blog.domain.auth.dto.request.SignUpRequestDto;
import efub.session.blog.domain.auth.dto.response.JwtResponseDto;
import efub.session.blog.global.jwt.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final PasswordEncoder passwordEncoder;
	private final AccountRepository accountRepository;
	private final JwtService jwtService;

	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtAuthenticationProvider jwtAuthenticationProvider;

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

	public JwtResponseDto login(String email, String password) {
		// 이메일 & 패스워드를 기반으로 Authentication 객체 생성
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
		// Authentication 객체 검증
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(token);
		// JWT 토큰 생성
		JwtResponseDto jwtResponseDto = jwtAuthenticationProvider.generateJwt(authentication);
		// Redis에 토큰 저장
		jwtService.saveJwtToken(authentication.getName(), jwtResponseDto.getAccessToken(),
			jwtResponseDto.getRefreshToken());
		return jwtResponseDto;
	}
}
