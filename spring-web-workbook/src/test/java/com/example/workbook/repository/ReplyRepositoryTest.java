package com.example.workbook.repository;

import com.example.workbook.domain.Board;
import com.example.workbook.domain.Reply;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * author        : duckbill413
 * date          : 2023-03-01
 * description   :
 **/
@SpringBootTest
@Log4j2
class ReplyRepositoryTest {
    @Autowired
    private ReplyRepository replyRepository;

    @DisplayName("reply insert test")
    @Test
    public void testInsert() {
        // 실제 DB에 있는 bno
        Long bno = 80L;

        Board board = Board.builder()
                .bno(bno).build();

        Reply reply = Reply.builder()
                .board(board)
                .replyText("댓글.......")
                .replier("replier1")
                .build();

        replyRepository.save(reply);
    }
    @DisplayName("board reply 조회 test")
    @Test
    public void testBoardReplies() {
        Long bno = 80L;

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("rno").descending());

        Page<Reply> result = replyRepository.listOfBoard(bno, pageRequest);

        result.getContent().forEach(reply -> {
            log.info(reply);
        });
    }
}