package efub.session.blog.domain.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import efub.session.blog.domain.account.domain.Account;
import efub.session.blog.domain.auth.dto.request.LoginRequestDto;
import efub.session.blog.domain.auth.dto.request.SignUpRequestDto;
import efub.session.blog.domain.auth.dto.response.JwtResponseDto;
import efub.session.blog.domain.auth.dto.response.SignUpResponseDto;
import efub.session.blog.domain.auth.service.AuthService;
import efub.session.blog.domain.auth.service.JwtService;
import efub.session.blog.global.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;
	private final JwtService jwtService;

	@PostMapping("/signup")
	@ResponseStatus(value = HttpStatus.CREATED)
	public SignUpResponseDto signUp(@RequestBody @Valid final SignUpRequestDto requestDto) {
		Account account = authService.signUp(requestDto);
		return new SignUpResponseDto(account);
	}

	@PostMapping("/login")
	@ResponseStatus(value = HttpStatus.OK)
	public JwtResponseDto login(@RequestBody final LoginRequestDto requestDto) {
		return authService.login(requestDto.getEmail(), requestDto.getPassword());
	}

	@PostMapping("/logout")
	public void logout(HttpServletRequest request) {
		String accessToken = JwtUtils.resolveToken(request);
		jwtService.removeJwtToken(accessToken);
	}
}
