package com.example.workbook.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

/**
 * author        : duckbill413
 * date          : 2023-03-08
 * description   :
 **/
@Getter
@Setter
@ToString
public class APIUserSecurityDTO extends User implements OAuth2User {
    private String mid;
    private String mpw;
    private String email;
    private boolean deleted;
    private boolean social;
    private Map<String, Object> props;
    public APIUserSecurityDTO(String username, String password,
                              String email, boolean deleted, boolean social,
                              Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);

        this.mid = username;
        this.mpw = password;
        this.email = email;
        this.deleted = deleted;
        this.social = social;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.getProps();
    }

    @Override
    public String getName() {
        return this.mid;
    }
}
