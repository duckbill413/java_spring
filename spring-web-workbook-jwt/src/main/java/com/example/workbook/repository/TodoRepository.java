package com.example.workbook.repository;

import com.example.workbook.domain.Todo;
import com.example.workbook.repository.search.TodoSearch;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * author        : duckbill413
 * date          : 2023-03-07
 * description   :
 **/

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoSearch {
}
