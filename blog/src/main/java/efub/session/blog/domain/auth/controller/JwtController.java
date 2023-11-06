package efub.session.blog.domain.auth.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import efub.session.blog.domain.auth.dto.response.RefreshTokenResponseDto;
import efub.session.blog.domain.auth.service.JwtService;
import efub.session.blog.global.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class JwtController {

	private final JwtService jwtService;

	// 토큰 재발급
	@PostMapping("/refresh")
	public RefreshTokenResponseDto refresh(HttpServletRequest request) {
		String accessToken = JwtUtils.resolveToken(request);
		return new RefreshTokenResponseDto(jwtService.refresh(accessToken));
	}
}
