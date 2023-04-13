package efub.session.blog.account.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static efub.session.blog.account.domain.AccountStatus.REGISTERED;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", updatable = false)
    private Long accountId;

    @Column(nullable = false, length = 60)
    private String email;

    @Column(nullable = false)
    private String encodedPassword;

    @Column(nullable = false, updatable = false, length = 16)
    private String nickname;

    private String bio;

    private AccountStatus status;

    @Builder
    public Account(String email, String password, String nickname, String bio) {
        this.email = email;
        this.encodedPassword = password;
        this.nickname = nickname;
        this.bio = bio;
        this.status = REGISTERED;
    }

    public void updateAccount(String bio, String nickname) {
        this.bio = bio;
        this.nickname = nickname;
    }

    public void withdrawAccount() {
        this.status = AccountStatus.UNREGISTERED;
    }
}
