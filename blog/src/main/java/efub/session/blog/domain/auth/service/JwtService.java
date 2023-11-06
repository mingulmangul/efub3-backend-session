package efub.session.blog.domain.auth.service;

import org.springframework.stereotype.Service;

import efub.session.blog.domain.auth.entity.JwtToken;
import efub.session.blog.domain.auth.repository.JwtRepository;
import efub.session.blog.global.jwt.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

	private final JwtRepository jwtRepository;
	private final JwtAuthenticationProvider jwtAuthenticationProvider;

	/**
	 * Redis에 JWT를 저장합니다.
	 * @param accountId 사용자 아이디(식별 값)
	 * @param accessToken 액세스 토큰
	 * @param refreshToken 리프레시 토큰
	 */
	public void saveJwtToken(String accountId, String accessToken, String refreshToken) {
		jwtRepository.save(new JwtToken(accountId, accessToken, refreshToken));
	}
}
