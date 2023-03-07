package com.example.workbook.controller;

import com.example.workbook.repository.APIUserRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.LifecycleState;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * author        : duckbill413
 * date          : 2023-03-05
 * description   :
 **/
@Log4j2
@RestController
@RequestMapping("/api/sample")
@RequiredArgsConstructor
public class SampleController {
    private final APIUserRepository apiUserRepository;
    @GetMapping("/doA")
    @Operation(summary = "Sample GET doA")
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<String> doA(){
        return Arrays.asList("AAA", "BBB", "CCC");
    }

    @GetMapping("/doB")
    @Operation(summary = "Sample GET doB")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<String> doB(){
        return Arrays.asList("AdminAAA", "AdminBBB", "AdminCCC");
    }

    @GetMapping("/auth")
    public ResponseEntity<?> getUserInfo(Authentication authentication){
        log.info("-------------------------");
        log.info("name: "+authentication.getName());
        log.info("isAuthenticated: " + authentication.isAuthenticated());
        log.info("principal: " + authentication.getPrincipal());
        log.info("authorities: " + authentication.getAuthorities());
        log.info("details: " + authentication.getDetails());
        log.info("credentials: " + authentication.getCredentials());

        return ResponseEntity.ok().body(apiUserRepository.findById(authentication.getName()).orElseThrow());
    }
}
