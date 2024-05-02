package talkd.talkd.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import talkd.talkd.DTO.BoardDTO;

// DB의 테이블 역할을 하는 클래스
@Entity
@Getter
@Setter
@Table(name = "community_table")
public class BoardEntity extends BaseEntity {

    @Id // pk 컬럼 지정. 필수
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long boardId;

    @Column // 크기 255
    private String boardType;

    @Column(nullable = false) // 크기 20, not null
    private String boardWriter;

    @Column // 크기 255
    private String boardPass;

    @Column(nullable = false)
    private String boardTitle;

    @Column(length = 500)
    private String boardContents;

    @Column
    private int boardHits;

    public static BoardEntity toSaveEntity(BoardDTO boardDTO){
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardType(boardDTO.getBoardType());
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(0);
        return boardEntity;
    }

    public static BoardEntity toUpdateEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        //setBoardId가 이번에는 무조건 들어가주어야 함 -> update쿼리 전달 요건: id
        boardEntity.setBoardId(boardDTO.getBoardId());

        boardEntity.setBoardType(boardDTO.getBoardType());
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(boardDTO.getBoardHits());
        return boardEntity;
    }
}
