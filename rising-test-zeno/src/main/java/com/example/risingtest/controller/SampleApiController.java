package com.example.risingtest.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SampleApiController {

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/doA")
    public List<String> doA() {
        return Arrays.asList("AAA","BBB","CCC");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/doB")
    public List<String> doB() {
        return Arrays.asList("AdminAAA","AdminBBB","AdminCCC");
    }
}