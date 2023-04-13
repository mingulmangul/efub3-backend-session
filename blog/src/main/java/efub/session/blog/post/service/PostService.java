package efub.session.blog.post.service;

import efub.session.blog.account.domain.Account;
import efub.session.blog.account.repository.AccountRepository;
import efub.session.blog.post.domain.Post;
import efub.session.blog.post.dto.PostModifyRequestDto;
import efub.session.blog.post.dto.PostRequestDto;
import efub.session.blog.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public Post addPost(PostRequestDto requestDto) {
        Account writer = accountRepository.findById(requestDto.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 계정입니다."));

        return postRepository.save(
                Post.builder()
                        .title(requestDto.getTitle())
                        .content(requestDto.getContent())
                        .writer(writer)
                        .build()
        );
    }

    public List<Post> findPostList() {
        return postRepository.findAll();
    }

    public Post findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
    }

    public void removePost(Long postId, Long accountId) {
        Post post = postRepository.findByPostIdAndAndWriter_AccountId(postId, accountId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 접근입니다."));
        postRepository.delete(post);
    }

    public Post modifyPost(Long postId, PostModifyRequestDto requestDto) {
        Post post = postRepository.findByPostIdAndAndWriter_AccountId(postId, requestDto.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 접근입니다."));
        post.updatePost(requestDto);
        return post;
    }
}
