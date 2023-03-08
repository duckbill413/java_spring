package com.example.workbook.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author        : duckbill413
 * date          : 2023-03-09
 * description   :
 **/
@Log4j2
@RestController
@RequestMapping("/login/oauth2/code")
public class OauthController {
    @GetMapping("/naver")
    public ResponseEntity naver(HttpServletRequest httpServletRequest, Authentication authentication){
        log.info(authentication.getName());


        return ResponseEntity.ok("sdfs");
    }
}
