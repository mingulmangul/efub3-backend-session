package efub.session.blog.board.repository;


import efub.session.blog.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Boolean existsByTitle(String title);


    Optional<Board> findByBoardIdAndOwner_AccountId(Long boardId, Long accountId); // boardId && Owner의 memberId를 기준으로 게시판 조회
}
