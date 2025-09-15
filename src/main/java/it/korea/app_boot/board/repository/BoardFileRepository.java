package it.korea.app_boot.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.korea.app_boot.board.entity.BoardFileEntity;

public interface BoardFileRepository extends JpaRepository<BoardFileEntity, Integer>{

}
