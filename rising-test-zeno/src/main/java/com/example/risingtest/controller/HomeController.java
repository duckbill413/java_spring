package com.example.risingtest.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {
    @ApiOperation("Home Page")
    @GetMapping("main")
    public String mainPage() {
        return "Home page";
    }
}
