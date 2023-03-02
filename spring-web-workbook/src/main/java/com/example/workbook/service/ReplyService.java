package com.example.workbook.service;

import com.example.workbook.dto.PageRequestDTO;
import com.example.workbook.dto.PageResponseDTO;
import com.example.workbook.dto.ReplyDTO;

/**
 * author        : duckbill413
 * date          : 2023-03-01
 * description   :
 **/

public interface ReplyService {
    Long register(ReplyDTO replyDTO);
    ReplyDTO read(Long bno);
    void modify(ReplyDTO replyDTO);
    void remove(Long bno);
    PageResponseDTO<ReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO);
}
