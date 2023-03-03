package com.example.workbook.service;

import com.example.workbook.domain.Board;
import com.example.workbook.dto.*;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
        // 첨부파일 추가
        boardDTO.setFileNames(Arrays.asList(
                UUID.randomUUID()+"_zzz.jpg"
        ));
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

//        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        PageResponseDTO<BoardListReplyCountDTO> responseDTO = boardService.listWithReplyCount(pageRequestDTO);
        log.info(responseDTO);
    }
    @DisplayName("게시물 등록 with 파일 첨부 테스트 DTO to Entity")
    @Test
    public void testRegisterWithImages() {
        log.info(boardService.getClass().getName());

        BoardDTO boardDTO = BoardDTO.builder()
                .title("File...Sample Title...")
                .content("Sample Content.....")
                .writer("user00")
                .build();

        boardDTO.setFileNames(Arrays.asList(
                UUID.randomUUID()+"_aaa.jpg",
                UUID.randomUUID()+"_bbb.jpg",
                UUID.randomUUID()+"_ccc.jpg"
        ));

        Long bno = boardService.register(boardDTO);

        log.info("bno: " + bno);
    }
    @DisplayName("게시물 조회 with 파일 첨부 테스트 Entity to DTO")
    @Test
    public void testReadAll(){
        Long bno = 101l;

        BoardDTO boardDTO = boardService.readOne(bno);

        log.info(boardDTO);

        for (String fileName: boardDTO.getFileNames()){
            log.info(fileName);
        }
    }
    @DisplayName("게시물 삭제 처리 orphanremoval=true 일 경우 댓글도 같이 삭제처리 된다.")
    @Test
    public void testRemoveAll() {
        Long bno = 1l;
        boardService.remove(bno);
    }
    @DisplayName("List with All Test")
    @Test
    public void testListWithAll() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<BoardListAllDTO> responseDTO = boardService.listWithAll(pageRequestDTO);

        List<BoardListAllDTO> dtoList = responseDTO.getDtoList();

        dtoList.forEach(boardListAllDTO -> {
            log.info(boardListAllDTO.getBno() + ":"+boardListAllDTO.getTitle());

            if (boardListAllDTO.getBoardImages() != null){
                for (BoardImageDTO boardImageDTO : boardListAllDTO.getBoardImages()){
                    log.info(boardImageDTO);
                }

                log.info("---------------------------");
            }
        });
    }
}