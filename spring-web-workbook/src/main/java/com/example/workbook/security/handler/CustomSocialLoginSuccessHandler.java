package com.example.workbook.security.handler;

import com.example.workbook.security.dto.MemberSecurityDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

/**
 * author        : duckbill413
 * date          : 2023-03-04
 * description   :
 **/
@Log4j2
@RequiredArgsConstructor
public class CustomSocialLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final String socialDefaultPwd = "1111";
    private final PasswordEncoder passwordEncoder;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO) authentication.getPrincipal();

        String encodedPwd = memberSecurityDTO.getMpw();

        if (memberSecurityDTO.isSocial() && (memberSecurityDTO.getMpw().equals(socialDefaultPwd) ||
                passwordEncoder.matches(socialDefaultPwd, memberSecurityDTO.getMpw()))){
//            response.sendRedirect("/member/modify");
            response.sendRedirect("/board/list");
        }
        else {
            response.sendRedirect("/board/list");
        }
    }
}
