package efub.session.blog.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import efub.session.blog.global.jwt.JwtAuthenticationProvider;
import efub.session.blog.global.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationProvider jwtAuthenticationProvider;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
			.csrf().disable()    // csrf 비활성화
			.formLogin().disable()    // form login 비활성화
			.httpBasic().disable()    // http basic authentication 비활성화
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)    // 세션 비활성화
			.and()
			.authorizeRequests()
			.antMatchers(HttpMethod.GET).permitAll()
			.antMatchers("/auth/**").permitAll() // 해당 URL에 대한 요청은 허용
			.anyRequest().authenticated()
			.and()
			.addFilterBefore(new JwtFilter(jwtAuthenticationProvider),
				UsernamePasswordAuthenticationFilter.class)    // 필터 추가
			.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
