package com.example.workbook.service;

import com.example.workbook.dto.PageRequestDTO;
import com.example.workbook.dto.PageResponseDTO;
import com.example.workbook.dto.TodoDTO;
import org.springframework.transaction.annotation.Transactional;

/**
 * author        : duckbill413
 * date          : 2023-03-07
 * description   :
 **/
@Transactional
public interface TodoService {
    Long register(TodoDTO todoDTO);
    TodoDTO read(Long tno);
    PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO);
    void remove(Long tno);
    void modify(TodoDTO todoDTO);
}
