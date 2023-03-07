package com.example.workbook.domain;

/**
 * author        : duckbill413
 * date          : 2023-03-07
 * description   :
 **/
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_todo_api")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tno;
    private String title;
    private LocalDate dueDate;
    private String writer;
    private boolean complete;

    public void changeComplete(boolean complete){
        this.complete = complete;
    }
    public void changeDueDate(LocalDate dueDate){
        this.dueDate = dueDate;
    }
    public void changeTitle(String title){
        this.title = title;
    }
}
