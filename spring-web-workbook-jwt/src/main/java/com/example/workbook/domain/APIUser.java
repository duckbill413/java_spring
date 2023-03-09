package com.example.workbook.domain;

/**
 * author        : duckbill413
 * date          : 2023-03-05
 * description   :
 **/
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = "roleSet")
@EqualsAndHashCode(exclude = "roleSet")
public class APIUser {
    @Id
    private String mid;
    private String mpw;
    private String email;
    private boolean deleted;
    private boolean social;
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Set<APIUserRole> roleSet = new HashSet<>();
    public void changePw(String mpw){
        this.mpw = mpw;
    }
    public void addRole(APIUserRole apiUserRole){
        this.roleSet.add(apiUserRole);
    }
    public void changeEmail(String email){
        this.email = email;
    }
}
