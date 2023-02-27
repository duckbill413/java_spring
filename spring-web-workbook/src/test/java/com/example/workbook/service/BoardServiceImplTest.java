package com.example.workbook.service;

import com.example.workbook.dto.BoardDTO;
import com.example.workbook.repository.BoardRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * author        : duckbill413
 * date          : 2023-02-27
 * description   :
 **/
@SpringBootTest
@Log4j2
class BoardServiceImplTest {
    @Autowired
    private BoardService boardService;

    @DisplayName("BoardService register test")
    @Test
    public void testRegister() {
        log.info(boardService.getClass().getName());

        BoardDTO boardDTO = BoardDTO.builder()
                .title("Sample title")
                .content("Sample content")
                .writer("duckbill")
                .build();

        Long bno = boardService.register(boardDTO);

        log.info("bno: " + bno);
    }
    @DisplayName("BoardService modify test")
    @Test
    public void testModify() {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(101L)
                .title("Updated title.....101")
                .content("Updated content.....101")
                .build();

        boardService.modify(boardDTO);
    }
    @DisplayName("BoardService PageRequest, PageResponse dto testList")
    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tcw")
                .keyword("2")
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);

        log.info(responseDTO);
    }
}