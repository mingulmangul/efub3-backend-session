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

	/**
	 * Redis에서 JWT를 제거합니다.
	 * @param accessToken 데이터를 식별할 액세스 토큰
	 */
	public void removeJwtToken(String accessToken) {
		JwtToken token = getJwtTokenByAccessToken(accessToken);
		jwtRepository.delete(token);
	}

	/**
	 * 새로운 액세스 토큰을 발급합니다.
	 * @param accessToken 기존 액세스 토큰
	 * @return 새로운 액세스 토큰
	 */
	public String refresh(String accessToken) {
		// 토큰 조회
		JwtToken token = getJwtTokenByAccessToken(accessToken);

		// 리프레시 토큰을 기반으로 액세스 토큰 재발급
		String generatedAccessToken = jwtAuthenticationProvider.generateAccessTokenByRefreshToken(token);

		// 토큰 정보 업데이트
		token.updateAccessToken(generatedAccessToken);
		jwtRepository.save(token);

		return generatedAccessToken;
	}

	/**
	 * Redis에서 access token을 기반으로 JWT를 조회합니다.
	 * @param accessToken 액세스 토큰
	 * @return 조회된 JWT
	 */
	private JwtToken getJwtTokenByAccessToken(String accessToken) {
		return jwtRepository.findByAccessToken(accessToken)
			.orElseThrow(() -> new IllegalArgumentException("There are no tokens stored."));
	}
}
