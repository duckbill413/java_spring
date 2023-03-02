package com.example.workbook.domain;

/**
 * author        : duckbill413
 * date          : 2023-03-01
 * description   :
 * Board (1) - Reply(*)
 * @Table 어노테이션에 인덱스 설정을 이용해서 인덱스를 지정할 수 있습니다.
 **/
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = "board", callSuper = true)
@EqualsAndHashCode(exclude = "board", callSuper = true)
@Table(name = "Reply", indexes = {@Index(name = "idx_reply_board_bno", columnList = "board_bno")})
public class Reply extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
    private String replyText;
    private String replier;
    public void changeText(String text){
        this.replyText = text;
    }
}
