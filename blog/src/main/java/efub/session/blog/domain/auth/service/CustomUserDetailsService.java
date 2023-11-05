package efub.session.blog.domain.auth.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import efub.session.blog.domain.account.domain.Account;
import efub.session.blog.domain.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final AccountRepository accountRepository;

	/**
	 * 데이터베이스로부터 username을 기반으로 유저의 정보를 가져옵니다.
	 * @param email 정보를 가져올 유저를 식별하는 username (여기서는 email을 username으로 사용)
	 * @return 유저 정보
	 * @throws UsernameNotFoundException 해당 username을 가진 유저가 없는 경우 예외가 발생합니다.
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return accountRepository.findByEmail(email)
			.map(this::createUserDetails)
			.orElseThrow(() -> new UsernameNotFoundException("User not found. email: " + email));
	}

	/**
	 * 유저 정보를 저장하는 UserDetails를 생성합니다.
	 * @param account 유저
	 * @return 유저 정보(UserDetails)
	 */
	private UserDetails createUserDetails(Account account) {
		return User.builder()
			.username(account.getEmail())
			.password(account.getEncodedPassword())
			.roles("USER")
			.build();
	}
}
