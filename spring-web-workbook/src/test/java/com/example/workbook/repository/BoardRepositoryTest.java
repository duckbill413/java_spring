package com.example.workbook.repository;

import com.example.workbook.domain.board.Board;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * author        : duckbill413
 * date          : 2023-02-25
 * description   :
 **/
@SpringBootTest
@Log4j2
class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @DisplayName("Board insert test")
    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Board board = Board.builder()
                    .title("title..." + i)
                    .content("content..." + i)
                    .writer("user" + (i % 10))
                    .build();
            Board result = boardRepository.save(board);
            log.info("BNO: " + result.getBno());
        });
    }
    @DisplayName("Board select test")
    @Test
    public void testSelect() {
        // given
        Long bno = 100L;
        // when
        Optional<Board> result = boardRepository.findById(bno);
        // then
        Board board = result.orElseThrow(RuntimeException::new);
        log.info(board);
    }
    @DisplayName("Board update test")
    @Test
    public void testUpdate() {
        // given
        Long bno = 100L;
        // when
        Optional<Board> result = boardRepository.findById(bno);
        // then
        Board board = result.orElseThrow(RuntimeException::new);
        board.change("update...title 100", "update content 100");
        boardRepository.save(board);
    }
    @DisplayName("Board delete test")
    @Test
    public void testDelete() {
        // given
        Long bno = 1L;
        // then
        boardRepository.deleteById(1L);
    }
    /**
     * Pageable과 Page<E> 타입
     * PageRequest.of(페이지 번호, 사이즈): 페이지 번호는 0 부터
     * PageRequest.of(페이지 번호, 사이즈, Sort): 정렬 조건 추가
     * PageRequest.of(페이지 번호, 사이즈, Sort, Direction, 속성...): 정렬 방향과 여러 속성 지정
     *
     */
    @DisplayName("Board Pageable test")
    @Test
    public void testPaging() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 10,
                Sort.by("bno").descending());

        // then
        Page<Board> result = boardRepository.findAll(pageRequest);

        log.info("page number: " + result.getNumber());
        log.info("total pages: " + result.getTotalPages());
        log.info("total count: " + result.getTotalElements());
        log.info("page size: " + result.getSize());

        List<Board> todoList = result.getContent();

        todoList.forEach(board -> log.info(board));
    }
    @DisplayName("Querydsl을 활용한 search1 테스트")
    @Test
    public void testSearch1() {
        // 2-page order by bno desc
        PageRequest pageRequest = PageRequest.of(1, 10, Sort.by("bno").descending());
        Page<Board> result = boardRepository.search1(pageRequest);

        result.get().forEach(board -> log.info(board));
    }

    @DisplayName("Querydsl을 활용한 searchAll 테스트")
    @Test
    public void testSearchAll() {
        // given
        String [] types = {"t", "c", "w"};
        // when
        String keyword = "4";
        PageRequest pageRequest = PageRequest.of(1, 10, Sort.by("bno").descending());

        // then
        Page<Board> result = boardRepository.searchAll(types, keyword, pageRequest);

        // total pages
        log.info("total pages: " + result.getTotalPages());
        // page size
        log.info("page size: " + result.getSize());
        // page number
        log.info("page number: " + result.getNumber());
        // prev next
        log.info("page hasPrevious: " + result.hasPrevious());

        result.getContent().forEach(board -> log.info(board));
    }
}