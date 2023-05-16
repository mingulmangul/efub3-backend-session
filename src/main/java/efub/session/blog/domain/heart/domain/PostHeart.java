package efub.session.blog.domain.heart.domain;

import efub.session.blog.domain.account.domain.Account;
import efub.session.blog.domain.post.domain.Post;
import efub.session.blog.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostHeart extends BaseTimeEntity {
    // 게시글 좋아요 id 작성
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_heart_id")
    private Long id;

    // 게시글 - Post 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "게시글은 필수로 입력되어야 합니다.")
    @JoinColumn(name = "post_id", updatable = false)
    private Post post;

    // 작성자 - Account 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "작성자는 필수로 입력되어야 합니다.")
    @JoinColumn(name = "account_id", updatable = false)
    private Account writer;

    // Builder 패턴을 통해 post, account 자동 생성
    @Builder
    public PostHeart(Post post, Account writer) {
        this.post = post;
        this.writer = writer;
    }
}
