package com.example.workbook.service;

import com.example.workbook.domain.Todo;
import com.example.workbook.dto.PageRequestDTO;
import com.example.workbook.dto.PageResponseDTO;
import com.example.workbook.dto.TodoDTO;
import com.example.workbook.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * author        : duckbill413
 * date          : 2023-03-07
 * description   :
 **/
@Service
@Log4j2
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final ModelMapper modelMapper;
    private final TodoRepository todoRepository;
    @Override
    public Long register(TodoDTO todoDTO) {
        Todo todo = modelMapper.map(todoDTO, Todo.class);

        Long tno = todoRepository.save(todo).getTno();

        return tno;
    }

    @Override
    public TodoDTO read(Long tno) {
        Optional<Todo> result = todoRepository.findById(tno);
        Todo todo = result.orElseThrow();

        TodoDTO todoDTO = modelMapper.map(todo, TodoDTO.class);
        return todoDTO;
    }

    @Override
    public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO) {
        Page<TodoDTO> result = todoRepository.list(pageRequestDTO);

        return PageResponseDTO.<TodoDTO> withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.toList())
                .total((int) result.getTotalElements())
                .build();
    }
    @Override
    public void remove(Long tno) {
        todoRepository.deleteById(tno);
    }
    @Override
    public void modify(TodoDTO todoDTO) {
        Optional<Todo> result = todoRepository.findById(todoDTO.getTno());

        Todo todo = result.orElseThrow();

        todo.changeTitle(todoDTO.getTitle());
        todo.changeDueDate(todoDTO.getDueDate());
        todo.changeComplete(todo.isComplete());

        todoRepository.save(todo);
    }
}
