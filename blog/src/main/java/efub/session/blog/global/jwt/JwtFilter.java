package efub.session.blog.global.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
	private static final String BEARER = "Bearer";

	private final JwtAuthenticationProvider jwtAuthenticationProvider;

	/**
	 * Request의 Authorization 헤더에서 토큰을 꺼냅니다.
	 * @param request
	 * @return 토큰
	 */
	private static String resolveToken(HttpServletRequest request) {
		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authorization == null || !authorization.startsWith(BEARER)) {
			return null;
		}
		return authorization.split(" ")[1];
	}

	/**
	 * JwtFilter에서 필터링을 수행하는 메소드입니다.
	 * @param request
	 * @param response
	 * @param filterChain
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		// Request 헤더로부터 토큰을 꺼냄
		String token = resolveToken(request);

		// 토큰이 유효한 경우 토큰에서 Authentication 객체를 생성한 후 SecurityContext에 저장
		if (token != null && jwtAuthenticationProvider.validateToken(token)) {
			SecurityContext context = SecurityContextHolder.createEmptyContext();
			Authentication authentication = jwtAuthenticationProvider.authenticate(token);
			context.setAuthentication(authentication);
			SecurityContextHolder.setContext(context);
			log.debug("[+] Token in SecurityContextHolder");
		}
		filterChain.doFilter(request, response);
	}
}
