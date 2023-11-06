package efub.session.blog.global.jwt;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import efub.session.blog.domain.auth.dto.response.JwtResponseDto;
import efub.session.blog.domain.auth.entity.JwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationProvider {

	private static final Long ACCESS_TOKEN_VALID_TIME = Duration.ofMinutes(30).toMillis(); // 만료시간 30분
	private static final Long REFRESH_TOKEN_VALID_TIME = Duration.ofDays(14).toMillis(); // 만료시간 2주

	private final SecretKey secretKey;

	/**
	 * JWT 시크릿 키를 Base64로 인코딩한 값으로 초기화한다.
	 */
	public JwtAuthenticationProvider(@Value("${jwt.secret}") String secret) {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		this.secretKey = Keys.hmacShaKeyFor(keyBytes);
	}

	/**
	 * JWT 토큰을 생성한다.
	 * @param authentication : 인증이 완료된 인증 객체
	 * @return JwtToken : JWT
	 */
	public JwtResponseDto generateJwt(Authentication authentication) {
		long now = new Date(System.currentTimeMillis()).getTime();

		// Access Token 생성
		String accessToken = generateAccessToken(authentication.getName(), authentication.getAuthorities(), now);

		// Refresh Token 생성
		String refreshToken = Jwts.builder()
			.expiration(new Date(now + REFRESH_TOKEN_VALID_TIME))
			.signWith(secretKey)
			.compact();

		return new JwtResponseDto(accessToken, refreshToken);
	}

	/**
	 * 액세스 토큰을 생성합니다.
	 * @param subject 토큰의 주체 (여기서는 accountId를 저장함)
	 * @param authorities 유저의 권한 정보
	 * @param now 현재 시각
	 * @return 액세스 토큰
	 */
	private String generateAccessToken(String subject,
		Collection<? extends GrantedAuthority> authorities, long now) {
		// 권한 정보를 문자열로 변환
		String auth = authorities.stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));

		return Jwts.builder()
			.subject(subject)
			.claim("auth", auth)
			.expiration(new Date(now + ACCESS_TOKEN_VALID_TIME))
			.signWith(secretKey)
			.compact();
	}

	/**
	 * 리프레시 토큰을 기반으로 액세스 토큰을 재발급합니다.
	 * @param token 토큰
	 * @return 새로 생성된 액세스 토큰
	 */
	public String generateAccessTokenByRefreshToken(JwtToken token) {
		// 리프레시 토큰 검증
		validateToken(token.getRefreshToken());

		// 액세스 토큰 정보 조회
		Claims claims = parseClaims(token.getAccessToken());
		Collection<? extends GrantedAuthority> authorities = getGrantedAuthorities(claims);

		// 액세스 토큰 발급
		long now = new Date(System.currentTimeMillis()).getTime();
		return generateAccessToken(claims.getSubject(), authorities, now);
	}

	/**
	 * 토큰에서 유저 정보를 꺼내 반환하는 메소드
	 * @param token
	 * @return 유저 인증 정보
	 */
	public Authentication authenticate(String token) {
		// 토큰 복호화
		// Claim: 토큰에 담는 데이터 한 조각(Key-Value 쌍)
		Claims claims = parseClaims(token);

		// 토큰에서 권한 정보를 꺼냄
		Collection<? extends GrantedAuthority> authorities = getGrantedAuthorities(claims);

		// 유저 인증 정보 생성
		UserDetails principal = new User(claims.getSubject(), "", authorities);
		log.debug("User principal: {}", principal.getUsername());
		return new UsernamePasswordAuthenticationToken(principal, "", authorities);
	}

	/**
	 * 토큰의 페이로드에서 유저의 권한 정보를 조회합니다.
	 * @param claims 토큰의 페이로드
	 * @return 권한 목록
	 */
	private Collection<? extends GrantedAuthority> getGrantedAuthorities(Claims claims) {
		// 토큰에 권한 정보가 없는 경우 예외 발생
		if (claims.get("auth") == null) {
			throw new SecurityException("Unauthorized JWT Token");
		}
		// 권한 정보를 꺼냄
		return Arrays.stream(claims.get("auth").toString().split(","))
			.map(SimpleGrantedAuthority::new)
			.collect(Collectors.toList());
	}

	/**
	 * 토큰을 복호화한 후 페이로드를 반환합니다.
	 * @param token 복호화할 토큰
	 * @return 토큰의 페이로드
	 */
	private Claims parseClaims(String token) {
		try {
			return Jwts.parser().verifyWith(secretKey).build()
				.parseSignedClaims(token).getPayload();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}

	/**
	 * 토큰을 검증합니다.
	 * @param token 검증할 토큰
	 * @return 유효성
	 */
	public boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
			log.debug("Success: Token validation");
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			log.info("Invalid JWT Token", e);
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT Token", e);
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT Token", e);
		} catch (IllegalArgumentException e) {
			log.info("JWT claims string is empty.", e);
		}
		return false;
	}
}
