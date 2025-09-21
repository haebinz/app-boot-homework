package it.korea.app_boot.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import it.korea.app_boot.board.dto.Board;
import it.korea.app_boot.board.dto.BoardSearchDTO;

@Mapper
public interface BoardMapper {


    int getBoardTotal();
    List<Board.Response> getBoardList(BoardSearchDTO searchDTO);

}
