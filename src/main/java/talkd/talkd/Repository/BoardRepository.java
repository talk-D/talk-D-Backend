package talkd.talkd.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import talkd.talkd.Entity.BoardEntity;

@Repository
// JpaRepository<CommunityEntity,Long> 여기에서 왼쪽인자는 받을 엔티티, 오른쪽인자는 pk 자료형
public interface BoardRepository extends JpaRepository<BoardEntity,Long> {

    @Modifying // delete 같은 쿼리는 modifying 어노테이션이 필수라고 함
    @Query(value="update BoardEntity b set b.boardHits=b.boardHits+1 where b.boardId=:boardId")
    void updateHits(@Param("boardId") Long boardId);

}
