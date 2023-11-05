package efub.session.blog.board.dto;

import efub.session.blog.board.domain.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// 게시판 응답메시지와 관련된 DTO
@Getter
@NoArgsConstructor
public class BoardResponseDto {

    private Long boardId;

    private String title;

    private String description;

    private String pinned;

    private Long ownerId;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    public BoardResponseDto (Board board) {
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.description = board.getDescription();
        this.pinned = board.getPinned();
        this.ownerId = board.getOwner().getAccountId();
        this.createdDate = board.getCreatedDate();
        this.modifiedDate = board.getModifiedDate();
    }

}