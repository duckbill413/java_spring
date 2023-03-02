package com.example.workbook.repository;

import com.example.workbook.domain.Board;
import com.example.workbook.repository.search.BoardSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * author        : duckbill413
 * date          : 2023-02-25
 * description   :
 **/

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {
    Page<Board> findByTitleContainingOrderByBnoDesc(String keyword, Pageable pageable);
    @Query("select b from Board b where b.title like concat('%', :keyword, '%') ")
    Page<Board> findKeyword(@Param("keyword") String keyword, Pageable pageable);

    /** Spring boot 3.0.3에서 nativeQuery error 문제
    @Query(value = "select now()", nativeQuery = true)
    String getTime();
    **/
    @EntityGraph(attributePaths = {"imageSet"})
    @Query("select b from Board b where b.bno = :bno")
    Optional<Board> findByWithImages(@Param("bno") Long bno);
}