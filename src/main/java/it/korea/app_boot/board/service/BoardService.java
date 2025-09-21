package it.korea.app_boot.board.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import it.korea.app_boot.board.dto.Board;
import it.korea.app_boot.board.dto.BoardSearchDTO;
import it.korea.app_boot.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper mapper;

    public Map<String, Object> getBoardList(BoardSearchDTO searchDTO) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();

        int total = mapper.getBoardTotal();
        List<Board.Response> boardList = new ArrayList<>();
        
        if(total > 0) {
            boardList = mapper.getBoardList(searchDTO);
        }

        resultMap.put("total", total);
        resultMap.put("content", boardList);

        return resultMap;
    }

  

}
