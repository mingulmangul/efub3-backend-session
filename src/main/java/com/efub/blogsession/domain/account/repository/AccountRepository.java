package com.efub.blogsession.domain.account.repository;

import com.efub.blogsession.domain.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
	Boolean existsByEmail(String email);

}
