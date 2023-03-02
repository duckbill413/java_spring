package com.example.workbook.service;

import com.example.workbook.dto.ReplyDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * author        : duckbill413
 * date          : 2023-03-01
 * description   :
 **/
@SpringBootTest
@Log4j2
class ReplyServiceImplTest {
    @Autowired
    private ReplyService replyService;

    @DisplayName("reply register test")
    @Test
    public void testRegister() {
        ReplyDTO replyDTO = ReplyDTO.builder()
                .replyText("ReplyDTO text")
                .replier("replier")
                .bno(91L)
                .build();

        Long rno = replyService.register(replyDTO);
        log.info(rno);
    }
}