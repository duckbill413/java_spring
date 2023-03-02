package com.example.workbook.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * author        : duckbill413
 * date          : 2023-03-01
 * description   :
 **/
@Data
public class BoardListReplyCountDTO {
    private Long bno;
    private String title;
    private String writer;
    private LocalDateTime regDate;
    private Long replyCount;
}
