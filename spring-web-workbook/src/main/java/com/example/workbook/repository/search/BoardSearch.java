package com.example.workbook.repository.search;

import com.example.workbook.domain.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * author        : duckbill413
 * date          : 2023-02-25
 * description   :
 **/

public interface BoardSearch {
    Page<Board> search1(Pageable pageable);
    Page<Board> searchAll(String [] types, String keyword, Pageable pageable);
}
