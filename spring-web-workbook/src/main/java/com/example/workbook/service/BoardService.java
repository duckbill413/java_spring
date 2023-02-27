package com.example.workbook.service;

import com.example.workbook.dto.BoardDTO;

/**
 * author        : duckbill413
 * date          : 2023-02-26
 * description   :
 **/
public interface BoardService {
    Long register(BoardDTO boardDTO);
    BoardDTO readOne(Long bno);
    void modify(BoardDTO boardDTO);
    void remove(Long bno);
    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);
}
