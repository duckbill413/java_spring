package com.example.workbook.repository;

import com.example.workbook.domain.Board;
import com.example.workbook.dto.BoardListAllDTO;
import com.example.workbook.dto.BoardListReplyCountDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

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
    @Autowired
    private ReplyRepository replyRepository;

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
        String[] types = {"t", "c", "w"};
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

    @DisplayName("Search Reply count Test")
    @Test
    public void testSearchReplyCount() {
        String[] types = {"t", "c", "w"};
        String keyword = "8";
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<BoardListReplyCountDTO> result = boardRepository.searchWithReplyCount(types, keyword, pageRequest);

        log.info("Total Page: " + result.getTotalPages());
        log.info("Page Size: " + result.getSize());
        log.info("Page Number: " + result.getNumber());
        log.info("Has Prev / Next : " + result.hasPrevious() + " / " + result.hasNext());

        result.getContent().forEach(board -> log.info(board));
    }

    @DisplayName("Board에 이미지 삽입 테스트")
    @Test
    public void testInsertWithImages() {
        Board board = Board.builder()
                .title("Image test")
                .content("첨부파일 테스트")
                .writer("tester")
                .build();

        for (int i = 0; i < 3; i++) {
            board.addImage(UUID.randomUUID().toString(), "file" + i + ".jpg");
        }

        boardRepository.save(board);
    }

    @DisplayName("read with images test")
    @Test
//    @Transactional
    public void testReadWithImages() {
//        Optional<Board> result = boardRepository.findById(105L);
        Optional<Board> result = boardRepository.findByWithImages(105L);
        Board board = result.orElseThrow();

        log.info(board);
        log.info("------------------");
//        log.info(board.getImageSet()); // Lazy Loading Error
        board.getImageSet().forEach(i -> log.info(i));
    }

    @DisplayName("modify image test - OrphanRemoval 속성")
    @Test
    @Commit
    @Transactional
    public void testModifyImages() {
        Optional<Board> result = boardRepository.findByWithImages(105L);

        Board board = result.orElseThrow();

        // 기존의 첨부 파일 삭제
        board.clearImages();

        // 새로운 첨부 파일 삽입
        for (int i = 0; i < 3; i++) {
            board.addImage(UUID.randomUUID().toString(), "updateFile" + i + ".jpg");
        }
        boardRepository.save(board);
    }

    @DisplayName("게시물 삭제시 댓글 같이 삭제 테스트")
    @Test
    @Transactional
    @Commit
    public void testRemoveAll() {
        Long bno = 105L;
        replyRepository.deleteByBoard_Bno(bno);
        boardRepository.deleteById(bno);
    }

    @DisplayName("N+1 문제 확인을 위한 삽입 테스트")
    @Test
    public void testInsertAll() {

        for (int i = 1; i <= 100; i++) {

            Board board = Board.builder()
                    .title("Title.." + i)
                    .content("Content.." + i)
                    .writer("writer.." + i)
                    .build();

            for (int j = 0; j < 3; j++) {

                if (i % 5 == 0) {
                    continue;
                }
                board.addImage(UUID.randomUUID().toString(), i + "file" + j + ".jpg");
            }
            boardRepository.save(board);

        }//end for
    }

    @Transactional
    @DisplayName("N+1 문제 확인을 위한 조회 테스트, @BatchSize 어노테이션을 Board domain의" +
            " BoardImage에 붙여 N번 쿼리를 모아서 한번에 실행시킬 수 있다.")
    @Test
    public void testSearchImageReplyCount() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("bno").descending());
        boardRepository.searchWithAll(null, null, pageRequest);
    }
    
    @Transactional
    @DisplayName("게시물 조회시 댓글 개수와 이미지를 같이 조회")
    @Test
    public void testSearchImageReplyCountAll() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<BoardListAllDTO> result = boardRepository.searchWithAll(null, null, pageRequest);

        log.info("-------------------------------------------");
        log.info(result.getTotalElements());

        result.getContent().forEach(boardListAllDTO -> log.info(boardListAllDTO));
    }
}