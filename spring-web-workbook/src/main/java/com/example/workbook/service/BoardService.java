package com.example.workbook.service;

import com.example.workbook.domain.Board;
import com.example.workbook.dto.*;

import java.util.List;
import java.util.stream.Collectors;

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
    PageResponseDTO<BoardListAllDTO> listWithAll(PageRequestDTO pageRequestDTO);
    // 댓글의 숫자까지 처리
    PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO);
    default Board dtoToEntity(BoardDTO boardDTO){
        Board board = Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(boardDTO.getWriter())
                .build();

        if (boardDTO.getFileNames() != null){
            boardDTO.getFileNames().forEach(s -> {
                String [] arr = s.split("_");
                board.addImage(arr[0], arr[1]);
            });
        }
        return board;
    }
    default BoardDTO entityToDTO(Board board){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .build();

        List<String> fileNames = board.getImageSet().stream().sorted().map(boardImage ->
                boardImage.getUuid() + "_" + boardImage.getFileName()).collect(Collectors.toList());

        boardDTO.setFileNames(fileNames);

        return boardDTO;
    }
}
