package com.example.workbook.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * author        : duckbill413
 * date          : 2023-03-05
 * description   :
 **/
@Getter
@Setter
@ToString
public class APIUserDTO extends User {
    private String mid;
    private String mpw;
    private String email;
    private boolean deleted;
    private boolean social;

    public APIUserDTO(String username, String password,
                      String email,
                      boolean deleted, boolean social,
                      Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);

        this.mid = username;
        this.mpw = password;
        this.email = email;
        this.deleted = deleted;
        this.social = social;
    }
}
