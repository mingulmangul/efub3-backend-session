package efub.session.blog.domain.account.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import efub.session.blog.domain.account.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	Optional<Account> findByEmail(String email);

	Boolean existsByEmail(String email);
}
