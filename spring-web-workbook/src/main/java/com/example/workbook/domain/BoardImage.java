package com.example.workbook.domain;

/**
 * author        : duckbill413
 * date          : 2023-03-02
 * description   :
 **/
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import jakarta.persistence.Entity;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = "board")
public class BoardImage implements Comparable<BoardImage>{
    @Id
    private String uuid;
    private String fileName;
    private int ord;
    @ManyToOne
    private Board board;

    @Override
    public int compareTo(BoardImage other) {
        return this.ord - other.ord;
    }
    public void changeBoard(Board board){
        this.board = board;
    }
}
