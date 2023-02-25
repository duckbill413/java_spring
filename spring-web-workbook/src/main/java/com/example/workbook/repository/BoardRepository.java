package com.example.workbook.repository;

import com.example.workbook.domain.board.Board;
import com.example.workbook.repository.search.BoardSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * author        : duckbill413
 * date          : 2023-02-25
 * description   :
 **/

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {
    Page<Board> findByTitleContainingOrderByBnoDesc(String keyword, Pageable pageable);
    @Query("select b from Board b where b.title like concat('%', :keyword, '%') ")
    Page<Board> findKeyword(String keyword, Pageable pageable);

    /** Spring boot 3.0.3에서 nativeQuery error 문제
    @Query(value = "select now()", nativeQuery = true)
    String getTime();
    **/
}