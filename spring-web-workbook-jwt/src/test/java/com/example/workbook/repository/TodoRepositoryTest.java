package com.example.workbook.repository;

import com.example.workbook.domain.Todo;
import com.example.workbook.dto.PageRequestDTO;
import com.example.workbook.dto.TodoDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.stream.IntStream;

/**
 * author        : duckbill413
 * date          : 2023-03-07
 * description   :
 **/
@SpringBootTest
@Log4j2
class TodoRepositoryTest {
    @Autowired
    private TodoRepository todoRepository;

    @DisplayName("Insert Todo Data Test")
    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Todo todo = Todo.builder()
                    .title("Todo..." + i)
                    .dueDate(LocalDate.of(2023, (i % 12) + 1, (i % 30) + 1))
                    .writer("user" + (i % 10))
                    .complete(false)
                    .build();

            todoRepository.save(todo);
        });
    }
    @DisplayName("Todo Search by Querydsl test")
    @Test
    public void testSearch() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .from(LocalDate.of(2023, 10, 01))
                .to(LocalDate.of(2023, 12, 31))
                .build();

        Page<TodoDTO> result = todoRepository.list(pageRequestDTO);

        result.forEach(System.out::println);
    }
}