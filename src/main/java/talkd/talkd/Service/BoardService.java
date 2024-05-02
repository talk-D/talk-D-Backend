package talkd.talkd.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkd.talkd.DTO.BoardDTO;
import talkd.talkd.Entity.BoardEntity;
import talkd.talkd.Repository.BoardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void save(BoardDTO boardDTO) {
    // 레퍼지토리는 entity 개체만 받음 BoardRepository.save();
        //DTO -> Entity
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
        boardRepository.save(boardEntity); //boardRepository.save() 이건 디비에 저장하는 내장함수임
    }

    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        // boardRepository.findAll()는 repository의 내장함수라 entity형임
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (BoardEntity boardEntity: boardEntityList){
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }
        return boardDTOList;
    }

    //jpa에서 제공하는 쿼리 외에 사용자가 직접 만든 쿼리라면 @transactional 붙여주어야 함
    @Transactional
    public void updateHits(Long boardId) {
        // 특수한 목적을 가진 쿼리들은 별도의 메서드를 정의할 필요가 있다.
        boardRepository.updateHits(boardId);
    }

    public BoardDTO findById(Long boardId) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(boardId);
        if (optionalBoardEntity.isPresent()){
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            return boardDTO;
        }
        else{
            return null;
        }
    }

    public BoardDTO update(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
        boardRepository.save(boardEntity);
        return findById(boardDTO.getBoardId());
        //findById는 옵셔널을 까주는 역할을 함
    }

    public void delete(Long boardId) {
        boardRepository.deleteById(boardId);
    }
}
//service에서는 Repository의 내장 함수들을 쓰는 곳인가봐
