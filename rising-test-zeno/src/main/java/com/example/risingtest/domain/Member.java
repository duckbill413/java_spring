package com.example.risingtest.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.example.risingtest.domain.base.BaseEntity;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Member extends BaseEntity {
    @Id @GeneratedValue
    private Long id;
    private String mid;
    private String mpw;
    public void changePw(String mpw){
        this.mpw = mpw;
    }
}
