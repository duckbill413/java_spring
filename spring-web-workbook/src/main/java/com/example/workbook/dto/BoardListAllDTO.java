package com.example.workbook.dto;

/**
 * author        : duckbill413
 * date          : 2023-03-03
 * description   :
 **/

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardListAllDTO {
    private Long bno;
    private String title;
    private String writer;
    private LocalDateTime regDate;
    private Long replyCount;
    private List<BoardImageDTO> boardImages;
}
