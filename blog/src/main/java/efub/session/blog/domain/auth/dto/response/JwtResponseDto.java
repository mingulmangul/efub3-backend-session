package efub.session.blog.domain.auth.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtResponseDto {

	private final String type = "Bearer";
	private final String accessToken;
	private final String refreshToken;
}
