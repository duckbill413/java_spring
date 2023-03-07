package com.example.workbook.domain;

/**
 * author        : duckbill413
 * date          : 2023-03-04
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
@ToString(callSuper = true, exclude = "roleSet")
@EqualsAndHashCode(callSuper = true, exclude = "roleSet")
public class Member extends BaseEntity {
    @Id
    private String mid;
    private String mpw;
    private String email;
    private boolean del;
    private boolean social;
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Set<MemberRole> roleSet = new HashSet<>();
    public void changePassword(String mpw){
        this.mpw = mpw;
    }
    public void changeEmail(String email){
        this.email = email;
    }
    public void changeDel(boolean del){
        this.del = del;
    }
    public void addRole(MemberRole memberRole){
        this.roleSet.add(memberRole);
    }
    public void clearRoles(){
        this.roleSet.clear();
    }
    public void changeSocial(boolean social){
        this.social = social;
    }
}
