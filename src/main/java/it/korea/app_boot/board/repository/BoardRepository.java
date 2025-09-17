package it.korea.app_boot.board.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.korea.app_boot.board.entity.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer>,  
                                            JpaSpecificationExecutor<BoardEntity> {


    //title검색
    Page<BoardEntity> findByTitleContaining(String title, Pageable pageable);

    //글쓴이 검색
    Page<BoardEntity> findByWriterContaining(String writer, Pageable pageable);

    @Query(value="""
            select b from BoardEntity b  
                left join fetch b.fileList 
            where b.brdId =:brdId
            """)
    Optional<BoardEntity>  getBoard(@Param("brdId")int brdId);

}
