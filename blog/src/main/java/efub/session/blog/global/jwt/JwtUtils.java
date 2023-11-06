package efub.session.blog.global.jwt;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUtils {

	private static final String BEARER = "Bearer";

	/**
	 * Request의 Authorization 헤더에서 토큰을 꺼냅니다.
	 * @param request 요청
	 * @return 토큰
	 */
	public static String resolveToken(HttpServletRequest request) {
		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authorization == null || !authorization.startsWith(BEARER)) {
			return null;
		}
		return authorization.split(" ")[1];
	}
}
