package com.example.risingtest.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@Log4j2
@Controller
public class RedirectController {
    @GetMapping("")
    public String redirectToHome(){
        return "redirect:/home/main";
    }
    @GetMapping("/swagger-ui")
    public String swaggerRedirect(){
        return "redirect:/swagger-ui/index.html";
    }
}
