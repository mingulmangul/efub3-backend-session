package efub.session.blog.domain.account.repository;

import efub.session.blog.domain.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Boolean existsByEmail(String email);
}
