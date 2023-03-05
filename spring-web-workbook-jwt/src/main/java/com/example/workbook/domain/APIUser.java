package com.example.workbook.domain;

/**
 * author        : duckbill413
 * date          : 2023-03-05
 * description   :
 **/
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Entity;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class APIUser {
    @Id
    private String mid;
    private String mpw;
    public void changePw(String mpw){
        this.mpw = mpw;
    }
}
