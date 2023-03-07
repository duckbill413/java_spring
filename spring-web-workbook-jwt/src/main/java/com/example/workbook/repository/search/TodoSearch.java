package com.example.workbook.repository.search;

import com.example.workbook.dto.PageRequestDTO;
import com.example.workbook.dto.TodoDTO;
import org.springframework.data.domain.Page;

/**
 * author        : duckbill413
 * date          : 2023-03-07
 * description   :
 **/

public interface TodoSearch {
    Page<TodoDTO> list(PageRequestDTO pageRequestDTO);
}
